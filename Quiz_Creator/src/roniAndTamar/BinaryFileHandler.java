package roniAndTamar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BinaryFileHandler {
	private ObjectOutputStream output;
	private ObjectInputStream input;

	public void openOutputFile(String fname) {
		try {
			output = new ObjectOutputStream(new FileOutputStream(fname));

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void openInputFile(String fname) {
		try {
			input = new ObjectInputStream(new FileInputStream(fname));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

	public void closeFileIn() {
		if (input != null) {
			try {
				input.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} // FileInputStream will be closed automatically
		}
	}

	public void closeFileOut() {
		if (output != null) {
			try {
				output.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void writeObject(Object obj) {
		if (output == null)
			return;
		try {
			output.writeObject(obj);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public Object readObject() {
		if (input == null)
			return null;
		try {
			return input.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
