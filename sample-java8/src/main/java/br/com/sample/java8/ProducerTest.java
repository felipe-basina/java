package br.com.sample.java8;

import java.util.function.Supplier;

public class ProducerTest {

	public static void main(String[] args) {
		Supplier<Person> supplier = () -> {
			return new Person("name", 32, "some user adddress");
		};
		
		if (supplier.get() != null) {
			System.out.println("supplier " + supplier.get());
		}
	}

}

class Person {

	private String name;
	private int age;
	private String address;

	public Person(String name, int age, String address) {
		this.name = name;
		this.age = age;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [name=").append(name).append(", age=").append(age).append(", address=").append(address)
				.append("]");
		return builder.toString();
	}

}
