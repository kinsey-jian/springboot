package demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class lamaba {
	public static void main(String[] args) {
		List<Car> list = new ArrayList<Car>();
		for(int i=0;i<1000000;i++){
			list.add(new Car("Tom"+i,new Random().nextInt(100)));
		}
		long a=System.currentTimeMillis();
		Iterator<Car> iterator = list.iterator();
		List<Car> car = new ArrayList<Car>();
		StringBuilder sb = new StringBuilder();
		while(iterator.hasNext()){
			Car next = iterator.next();
			if(next.getYear()>20)
				car.add(next);
		}
		Collections.sort(car, new Comparator<Car>() {
			@Override
			public int compare(Car o1, Car o2) {
				return new Integer(o1.getYear()).compareTo(o2.getYear());
			}
		});
		Iterator<Car> it = car.iterator();
		while(it.hasNext()){
			Car next = it.next();
			sb.append(next.getName()+", ");
		}
		String aa= sb.toString();
		aa.substring(0, aa.length()-1);
		long st = System.currentTimeMillis();
		System.out.println(st-a);
		String str = list.stream()
			.filter( s-> s.getYear()>20)
			.sorted(Comparator.comparing(Car::getYear))
			.map(Car::getName)
			.collect(Collectors.joining(", "));
		long en = System.currentTimeMillis();
		System.out.println(en-st);
		
		
	}
}
