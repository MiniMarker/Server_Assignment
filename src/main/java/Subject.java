/**
 * @author Christian on 02.11.2017.
 */

import javax.persistence.*;

@Entity
@NamedQueries(value = {
		@NamedQuery(name = "Subject.getAll", query = "SELECT b FROM Subject b")
})

public class Subject {

	@Id
	private String code;
	private String name;
	private double duration;
	private int numStudents;

	public Subject(){

	}

	public Subject(String code, String name, double duration, int numStudents) {
		this.code = code;
		this.name = name;
		this.duration = duration;
		this.numStudents = numStudents;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public int getNumStudents() {
		return numStudents;
	}

	public void setNumStudents(int numStudents) {
		this.numStudents = numStudents;
	}

	@Override
	public String toString() {
		return "Subject{" +
				"code='" + code + '\'' +
				", name='" + name + '\'' +
				", duration=" + duration +
				", numStudents=" + numStudents +
				'}';
	}
}