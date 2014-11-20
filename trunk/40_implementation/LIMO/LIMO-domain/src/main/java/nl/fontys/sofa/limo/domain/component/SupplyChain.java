package nl.fontys.sofa.limo.domain.component;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;

/**
 * Holder of a complete supply chain with actors, hubs, legs and events. Can be
 * stored to file and recreated from file.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class SupplyChain implements Serializable {

	private String name;
	private String filepath;
	private Hub startHub;
	private List<Actor> actors;


	public SupplyChain() {
		this.actors = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public Hub getStart() {
		return startHub;
	}

	public void setStart(Hub startHub) {
		this.startHub = startHub;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	/**
	 * Recreates a supply chain stored in the given file.
	 *
	 * @return Recreated supply chain.
	 */
	public static SupplyChain createFromFile(File file) {
		return null;
	}

	/**
	 * Saves the supply chain to a file specified at filepath.
	 */
	public void saveToFile() {
	}

}