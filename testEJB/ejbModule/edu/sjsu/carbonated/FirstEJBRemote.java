package edu.sjsu.carbonated;
import java.io.Serializable;

public interface FirstEJBRemote extends Serializable {
	String echo(String filename);
}
