import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Bfk {
	int[] cells;
	final int NUM_CELLS = 30000;
	int pointer;

	public Bfk() {
		cells = new int[NUM_CELLS];
		pointer = 0;
	}

	// TODO Write interpreter
	public void run() {
	}

	public void run(String filename) throws Exception {
		char[] program = parse(filename);
		Scanner in = new Scanner(System.in);
		for (int programPointer = 0; programPointer < program.length; programPointer++) {
			switch (program[programPointer]) {
			case '>': // > Move the pointer to the right
				pointer++;
				break;
			case '<': // < Move the pointer to the left
				pointer--;
				break;
			case '+': // + Increment the memory cell under the pointer
				cells[pointer]++;
				break;
			case '-': // - Decrement the memory cell under the pointer
				cells[pointer]--;
				break;
			case '.': // . Output the character signified by the cell at the pointer
				System.out.print((char) cells[pointer]);
				break;
			case ',': // , Input a character and store it in the cell at the pointer
				cells[pointer] = in.nextInt();
				break;
			case '[': // [ Jump past the matching ] if the cell under the pointer is 0
				if (cells[pointer] == 0) {
					while (true) {
						int lBracketCount = 0;
						programPointer++;
						if (program[programPointer] == '[') {
							lBracketCount++;
						} else if (program[programPointer] == ']') {
							if (lBracketCount == 0)
								break;
							else
								lBracketCount--;
						}
					}
				}
				break;
			case ']': // ] Jump back to the matching [ if the cell under the pointer is nonzero
				if (cells[pointer] != 0) {
					int rBracketCount = 0;
					while (true) {
						programPointer--;
						if (program[programPointer] == ']') {
							rBracketCount++;
						} else if (program[programPointer] == '[') {
							if (rBracketCount == 0)
								break;
							else
								rBracketCount--;
						}
					}
				}
				break;
			default:
				throw new Exception("Invalid source symbol");
			}
		}
		in.close();
	}

	public char[] parse(String filename) {
		String program = "";
		try (Scanner in = new Scanner(new File(filename))) {
			while (in.hasNextLine()) {
				program += in.nextLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return program.toCharArray();
	}

	public static void main(String[] args) {
		Bfk bfk = new Bfk();
		if (args.length == 0) { // Run interpreter
			bfk.run();
		} else if (args.length != 1) {
			System.out.println("Must pass in bfk source file");
		} else { // Run file
			try {
				bfk.run(args[0]);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
}
