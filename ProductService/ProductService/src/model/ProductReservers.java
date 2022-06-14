package model;

public class ProductReservers {
	private int id;
	private int reserverId;
	
	public ProductReservers() {
		super();
	}

	public ProductReservers(int id, int reserverId) {
		super();
		this.id = id;
		this.reserverId = reserverId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReserverId() {
		return reserverId;
	}

	public void setReserverId(int reserverId) {
		this.reserverId = reserverId;
	}

	@Override
	public String toString() {
		return "ProductReservers [id=" + id + ", reserverId=" + reserverId + "]";
	}

}

