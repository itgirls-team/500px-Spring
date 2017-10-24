package com.example.s8.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Diuner {

	private String hlebche;
	private String sosche = "mlechno";
	private int gramaj = 10;
	private int kartofi;
	private String meso;
	private ArrayList<String> zelencuchita = new ArrayList<>();

	public Diuner(){
		zelencuchita.add("Morkova");
		zelencuchita.add("Repata");
		zelencuchita.add("Kompira");
		zelencuchita.add("Salatata");
		zelencuchita.add("Celinata");
	}
	
	public String getHlebche() {
		return hlebche;
	}
	public void setHlebche(String hlebche) {
		this.hlebche = hlebche;
	}
	public String getSosche() {
		return sosche;
	}
	public void setSosche(String sosche) {
		this.sosche = sosche;
	}
	public int getGramaj() {
		return gramaj;
	}
	public void setGramaj(int gramaj) {
		this.gramaj = gramaj;
	}
	public int getKartofi() {
		return kartofi;
	}
	public void setKartofi(int kartofi) {
		this.kartofi = kartofi;
	}
	public String getMeso() {
		return meso;
	}
	public void setMeso(String meso) {
		this.meso = meso;
	}
	@Override
	public String toString() {
		return "Diuner [hlebche=" + hlebche + ", sosche=" + sosche + ", gramaj=" + gramaj + ", kartofi=" + kartofi
				+ ", meso=" + meso + "]";
	}
	
	public List<String> getZelencuchita() {
		return Collections.unmodifiableList(zelencuchita);
	}
	
	
}
