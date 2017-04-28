package ru.sbrf.ufs.mwp.jaxrs.valueobjects;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class TestRequestVO {

	private Date date;
	private Integer integer;
	private int primitiveInt;
	private Long objectLong;
	private long primitiveLong;
	private BigInteger bigInteger;
	private BigDecimal bigDecimal;
	private float primitiveFloat;
	private Float objectFloat;
	private CommonResult commonResult;
	private boolean primitiveBoolean;
	private Boolean objectBoolean;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getInteger() {
		return integer;
	}

	public void setInteger(Integer integer) {
		this.integer = integer;
	}

	public int getPrimitiveInt() {
		return primitiveInt;
	}

	public void setPrimitiveInt(int primitiveInt) {
		this.primitiveInt = primitiveInt;
	}

	public Long getObjectLong() {
		return objectLong;
	}

	public void setObjectLong(Long objectLong) {
		this.objectLong = objectLong;
	}

	public long getPrimitiveLong() {
		return primitiveLong;
	}

	public void setPrimitiveLong(long primitiveLong) {
		this.primitiveLong = primitiveLong;
	}

	public BigInteger getBigInteger() {
		return bigInteger;
	}

	public void setBigInteger(BigInteger bigInteger) {
		this.bigInteger = bigInteger;
	}

	public BigDecimal getBigDecimal() {
		return bigDecimal;
	}

	public void setBigDecimal(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}

	public float getPrimitiveFloat() {
		return primitiveFloat;
	}

	public void setPrimitiveFloat(float primitiveFloat) {
		this.primitiveFloat = primitiveFloat;
	}

	public Float getObjectFloat() {
		return objectFloat;
	}

	public void setObjectFloat(Float objectFloat) {
		this.objectFloat = objectFloat;
	}

	public CommonResult getCommonResult() {
		return commonResult;
	}

	public void setCommonResult(CommonResult commonResult) {
		this.commonResult = commonResult;
	}

	public boolean isPrimitiveBoolean() {
		return primitiveBoolean;
	}

	public void setPrimitiveBoolean(boolean primitiveBoolean) {
		this.primitiveBoolean = primitiveBoolean;
	}

	public Boolean getObjectBoolean() {
		return objectBoolean;
	}

	public void setObjectBoolean(Boolean objectBoolean) {
		this.objectBoolean = objectBoolean;
	}
}
