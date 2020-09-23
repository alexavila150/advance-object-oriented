public abstract class Person {
	protected String firstName;
	protected String lastName;
	protected String dob;
	protected String address;
	protected String phone;
	
	public Person(){}
	
	public Person(String firstName, String lastName, String dob, String address, String phone){
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.address = address;
		this.phone = phone;
	}
	
	/******************************************************************************************************************
	 *                                          Getters
	 * ***************************************************************************************************************/
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getDob() {
		return dob;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getPhone() {
		return phone;
	}
	
	/******************************************************************************************************************
	 *                                          Setters
	 * ***************************************************************************************************************/
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setDob(String dob) {
		this.dob = dob;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
