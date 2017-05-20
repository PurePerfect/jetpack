package com.pureperfect.jetpack.json;

import java.util.Iterator;

import org.junit.Test;

import com.pureperfect.jetpack.AnnotationReader;
import com.pureperfect.jetpack.Field;
import com.pureperfect.jetpack.FieldReader;
import com.pureperfect.jetpack.SlightlyFasterAnnotationReader;
import com.pureperfect.jetpack.mock.MappedTypeStub;

public class FieldReaderBenchmark
{
	@Test
	public void mappedFields()
	{
		runSimpleBenchmark(new AnnotationReader(JSON.class));
		runSimpleBenchmark(new SlightlyFasterAnnotationReader(JSON.class));
	}
	
	public void runSimpleBenchmark(FieldReader reader)
	{
		System.out.println("Running benchmark for: " + reader.getClass().getName());
		
		MappedTypeStub o = new MappedTypeStub();

		o.setId(23);
		o.setIq(190);
		o.setName("myname");

		long cumulativeTime = 0;

		for (int c = 0; c < 5; ++c)
		{
			long start = System.currentTimeMillis();
			
			for (int i = 0; i < 500000; ++i)
			{
				Iterator<Field> it = reader.read(o);

				while (it.hasNext())
					it.next();
			}
			
			cumulativeTime += System.currentTimeMillis() - start; 
		}	
		
		
		System.out.println("Total elapsed time (ms): " + cumulativeTime);
	}
}
