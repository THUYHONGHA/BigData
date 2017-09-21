

import java.io.IOException;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
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


public class WordCountCombiner {

	public static class Map extends
			Mapper<LongWritable, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
		private MapWritable mw;

		// method setup
		public void setup() {
			mw = new MapWritable();
		}

		// method map
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			StringTokenizer tokenizer = new StringTokenizer(line);

			while (tokenizer.hasMoreTokens()) {
				Text txtKey = new Text();
				txtKey.set(tokenizer.nextToken());
				if (mw.containsKey(txtKey)) {
					IntWritable w = (IntWritable) mw.get(txtKey);
					int count = w.get();
					count++;
					mw.put(txtKey, new IntWritable(count));
				} else {
					mw.put(txtKey, one);
				}

			}
		}

		// method cleanup
		public void cleanup(Context context) {

			Set<Writable> keys = mw.keySet();
			for (Writable key : keys) {
				try {
					context.write((Text) key, (IntWritable) mw.get(key));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
		public static class Reduce extends
				Reducer<Text, IntWritable, Text, IntWritable> {

			public void reduce(Text key, Iterable<IntWritable> values,
					Context context) throws IOException, InterruptedException {
				int sum = 0;
				for (IntWritable val : values) {
					sum += val.get();
				}
				context.write(key, new IntWritable(sum));
			}
		}

		public static void main(String[] args) throws Exception {
		    Configuration conf = new Configuration();
		    
		    Job job = new Job(conf, "wordcount");
		    job.setJarByClass(WordCountCombiner.class);
		    
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