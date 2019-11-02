package com.bh.live.pojo.res.inform;

import com.google.common.collect.Lists;

import java.util.List;


public class DewdropResultRes {
	private List<String> list;
	private int single;
	private int doubles;
	private int big;
	private int small;
	private int dragon;
	private int flat;
	private int tiger;
	private int prime;//质数
	private int composite;//和数
	
	private int eat;
	private int mantissaBig;
	private int mantissaSmall;
	private int sumSingle;
	private int sumDoubles;
	public List<String> getList() {
		if(list == null){
			return Lists.newArrayList();
		}
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public int getSingle() {
		return single;
	}
	public void setSingle(int single) {
		this.single = single;
	}
	public int getDoubles() {
		return doubles;
	}
	public void setDoubles(int doubles) {
		this.doubles = doubles;
	}
	public int getBig() {
		return big;
	}
	public void setBig(int big) {
		this.big = big;
	}
	public int getSmall() {
		return small;
	}
	public void setSmall(int small) {
		this.small = small;
	}
	public int getDragon() {
		return dragon;
	}
	public void setDragon(int dragon) {
		this.dragon = dragon;
	}
	public int getTiger() {
		return tiger;
	}
	public void setTiger(int tiger) {
		this.tiger = tiger;
	}
	public int getPrime() {
		return prime;
	}
	public void setPrime(int prime) {
		this.prime = prime;
	}
	public int getComposite() {
		return composite;
	}
	public void setComposite(int composite) {
		this.composite = composite;
	}
	public int getFlat() {
		return flat;
	}
	public void setFlat(int flat) {
		this.flat = flat;
	}
	public int getEat() {
		return eat;
	}
	public void setEat(int eat) {
		this.eat = eat;
	}
	public int getMantissaBig() {
		return mantissaBig;
	}
	public void setMantissaBig(int mantissaBig) {
		this.mantissaBig = mantissaBig;
	}
	public int getMantissaSmall() {
		return mantissaSmall;
	}
	public void setMantissaSmall(int mantissaSmall) {
		this.mantissaSmall = mantissaSmall;
	}
	public int getSumSingle() {
		return sumSingle;
	}
	public void setSumSingle(int sumSingle) {
		this.sumSingle = sumSingle;
	}
	public int getSumDoubles() {
		return sumDoubles;
	}
	public void setSumDoubles(int sumDoubles) {
		this.sumDoubles = sumDoubles;
	}
	
	
}
