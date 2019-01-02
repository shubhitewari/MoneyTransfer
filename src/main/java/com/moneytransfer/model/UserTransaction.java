package com.moneytransfer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class UserTransaction {


	@JsonProperty(required = true)
	private BigDecimal amount;

	@JsonProperty(required = true)
	private String fromAccountId;

	@JsonProperty(required = true)
	private String toAccountId;

	public UserTransaction() {
	}

	public UserTransaction(BigDecimal amount, String fromAccountId, String toAccountId) {
		this.amount = amount;
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
	}


	public BigDecimal getAmount() {
		return amount;
	}

	public String getFromAccountId() {
		return fromAccountId;
	}

	public String getToAccountId() {
		return toAccountId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserTransaction that = (UserTransaction) o;

		if (!amount.equals(that.amount))
			return false;
		if (!fromAccountId.equals(that.fromAccountId))
			return false;
		return toAccountId.equals(that.toAccountId);

	}

	@Override
	public int hashCode() {
		int result = amount.hashCode();
		result = 31 * result + fromAccountId.hashCode();
		result = 31 * result + toAccountId.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "UserTransaction{" + "amount=" + amount + ", fromAccountId="
				+ fromAccountId + ", toAccountId=" + toAccountId + '}';
	}

}
