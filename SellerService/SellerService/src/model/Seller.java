package model;

public class Seller {
	private int id;
	private String name;
	private String email;
	private String password;
	private String createdAt;
	private String updatedAt;
	private int sellerCategory;
	private String token;
	
	public Seller() {
		super();
	}

	public Seller(String name, String email, String password, int sellerCategory) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.sellerCategory = sellerCategory;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public int getSellerCategory() {
		return sellerCategory;
	}

	public void setSellerCategory(int sellerCategory) {
		this.sellerCategory = sellerCategory;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "Seller [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", sellerCategory=" + sellerCategory
				+ ", token=" + token + "]";
	}

	
}
