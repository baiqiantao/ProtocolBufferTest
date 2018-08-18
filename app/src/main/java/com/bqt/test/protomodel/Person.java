package com.bqt.test.protomodel;

public class Person {
	
	private String email;
	private int id;
	private String name;
	
	private Person(Builder builder) {
		email = builder.email;
		id = builder.id;
		name = builder.name;
	}
	
	public static Builder newBuilder() {
		return new Builder();
	}
	
	public static final class Builder {
		private String email;
		private int id;
		private String name;
		
		private Builder() {
		}
		
		public Builder email(String val) {
			email = val;
			return this;
		}
		
		public Builder id(int val) {
			id = val;
			return this;
		}
		
		public Builder name(String val) {
			name = val;
			return this;
		}
		
		public Person build() {
			return new Person(this);
		}
	}
	
	@Override
	public String toString() {
		return "Person{" +
				"email='" + email + '\'' +
				", id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
