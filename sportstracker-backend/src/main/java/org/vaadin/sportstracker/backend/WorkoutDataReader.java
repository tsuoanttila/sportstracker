package org.vaadin.sportstracker.backend;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import elemental.json.Json;
import elemental.json.JsonObject;

@Component
class WorkoutDataReader implements CommandLineRunner {

	@Autowired
	WorkoutRepository repo;

	@Override
	public void run(String... arg0) throws Exception {
		Stream.of(38, 39, 40, 41).map(i -> "week-" + i + ".json").map(this::readFile).map(Json::parse)
				.map(json -> json.getArray("workouts")).forEach(jsonArray -> {
					for (int i = 0; i < jsonArray.length(); ++i) {
						JsonObject workout = jsonArray.get(i);
						LocalDate date = LocalDate.parse(workout.getString("date"),
								DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
						Sport sport = Sport.valueOf(workout.getString("sport").toUpperCase());
						Integer duration = (int) workout.getNumber("duration");
						Integer calories = (int) workout.getNumber("calories");
						Workout workout2 = new Workout(date, sport, duration, calories);
						repo.save(workout2);
					}
				});
	}

	private String readFile(String file) {
		try {
			InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(file);
			return IOUtils.toString(resourceAsStream, Charset.defaultCharset());
		} catch (IOException e) {
			return "{}";
		}
	}

}