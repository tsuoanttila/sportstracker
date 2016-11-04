package org.vaadin.sportstracker.backend;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Workout {

	@Id
	@GeneratedValue
	private Long id;

	private LocalDate date;
	private Sport sport;
	private Integer duration;
	private Integer calories;

	public Workout() {
	}

	public Workout(LocalDate date, Sport sport, Integer duration, Integer calories) {
		this.date = date;
		this.sport = sport;
		this.duration = duration;
		this.calories = calories;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Sport getSport() {
		return sport;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	@Override
	public String toString() {
		return "{ Date: " + getDate().toString() + ", Sport: " + getSport() + ", Duration: " + getDuration()
				+ ", Calories: " + getCalories() + " }";
	}
}