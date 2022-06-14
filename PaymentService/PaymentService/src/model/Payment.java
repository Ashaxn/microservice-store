package model;

public class Payment {
	private int id;
	private int recipientId;
	private double total;
	private String paymentMethod;
	private String paidAt;
	private int sellerId;
	private Integer buyerId;
	private Integer reserverId;
	
	public Payment() {
		super();
	}

	public Payment(int recipientId, double total, String paymentMethod,
			int sellerId, Integer buyerId, Integer reserverId) {
		super();
		this.recipientId = recipientId;
		this.total = total;
		this.paymentMethod = paymentMethod;
		this.sellerId = sellerId;
		this.buyerId = buyerId;
		this.reserverId = reserverId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(int recipientId) {
		this.recipientId = recipientId;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(String paidAt) {
		this.paidAt = paidAt;
	}


	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public Integer getReserverId() {
		return reserverId;
	}

	public void setReserverId(Integer reserverId) {
		this.reserverId = reserverId;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", recipientId=" + recipientId + ", total=" + total + ", paymentMethod="
				+ paymentMethod + ", paidAt=" + paidAt + ", sellerId=" + sellerId + ", buyerId=" + buyerId
				+ ", reserverId=" + reserverId + "]";
	}

	

}
