package org.vaadin.sportstracker;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.vaadin.sportstracker.backend.Workout;
import org.vaadin.sportstracker.backend.WorkoutRepository;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;

@SpringBootApplication
public class SportstrackerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SportstrackerApplication.class, args);
	}
}

@Component("vaadinServlet")
class CustomVaadinServlet extends SpringVaadinServlet {

	@Override
	protected void servletInitialized() throws ServletException {
		super.servletInitialized();

		getService().addSessionInitListener(e -> {
			e.getSession().addBootstrapListener(new BootstrapListener() {

				@Override
				public void modifyBootstrapPage(BootstrapPageResponse response) {
					response.getDocument().head().append("<script src=\"/app.js\"></script>");
					response.getDocument().head().append("<link rel=\"manifest\" href=\"manifest.json\">");
				}

				@Override
				public void modifyBootstrapFragment(BootstrapFragmentResponse response) {
					// TODO Auto-generated method stub

				}
			});
		});
	}

}

@SpringUI
@Theme("valo")
@Viewport("width=device-width, initial-scale=1")
class SportstrackerUI extends UI {

	@Autowired
	WorkoutRepository repo;

	@Override
	protected void init(VaadinRequest request) {
		Grid grid = new Grid();
		grid.setContainerDataSource(new BeanItemContainer<>(Workout.class, repo.findAll()));
		grid.setSizeFull();

		setContent(grid);
	}

}