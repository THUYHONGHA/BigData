

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class WordCountAverageCombiner {

	public static class Map extends Mapper<Writable, Text, Text, Writable> {

		private static java.util.Map<Writable, Writable> mw;

		private final static IntWritable one = new IntWritable(1);

		@Override
		protected void setup(
				Mapper<Writable, Text, Text, Writable>.Context context)
				throws IOException, InterruptedException {
			super.setup(context);

			mw = new MapWritable();
		}

		public void map(Writable key, Text value, Context context) throws IOException, InterruptedException {
	            String line = value.toString();
	            StringTokenizer token = new StringTokenizer(line);

	            String first = "0";
	            String last = "0";

	            if (token.hasMoreTokens())
	                first = token.nextToken();

	            while (token.hasMoreTokens())
	                last = token.nextToken();

	            try {
	                Text word = new Text(first);
	                IntWritable val = new IntWritable(Integer.valueOf(last));

	                Pair<IntWritable, IntWritable> pair = (Pair<IntWritable, IntWritable>) mw.get(word);
	                if (mw.containsKey(word)) {
	                    mw.put(word, (Writable) new Pair<IntWritable, IntWritable>(
	                            new IntWritable(pair.getKey().get() + 1),
	                            new IntWritable(pair.getValue().get() + val.get())));
	                } else {
	                    mw.put(word, (Writable) new Pair<IntWritable, IntWritable>(one, val));
	                }
	            } catch (NumberFormatException e) {

	            }
	        }

		@Override
		protected void cleanup(
				Mapper<Writable, Text, Text, Writable>.Context context)
				throws IOException, InterruptedException {
			for (java.util.Map.Entry<Writable, Writable> entry : mw.entrySet()) {
				Text key = (Text) entry.getKey();
				Pair<IntWritable, IntWritable> pair = (Pair<IntWritable, IntWritable>) entry
						.getValue();
				IntWritable average = new IntWritable(pair.getValue().get()
						/ pair.getKey().get());
				context.write(key, average);
			}

			super.cleanup(context);
		}
	}

	public static class Reduce extends Reducer<Text, Writable, Text, Writable> {

		public void reduce(Text key, Iterable<Writable> values, Context context)
				throws IOException, InterruptedException {
			for (Writable value : values) {
				context.write(key, value);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		Job job = new Job(conf, "wordcount");
		job.setJarByClass(WordCountAverageCombiner.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.waitForCompletion(true);
	}

}
