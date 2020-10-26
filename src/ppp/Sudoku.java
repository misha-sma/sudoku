package ppp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

public class Sudoku {
	private static final int[][] MATRIX = new int[9][9];
	private static final int[][] MATRIX_ORIGINAL = new int[9][9];

	private static void loadMatrix(String path) {
		try {
			byte[] fileContent = Files.readAllBytes(new File(path).toPath());
			String text = new String(fileContent);
			String[] lines = text.split("\n");
			if (lines.length < 9) {
				System.out.println("Lines count<9");
				return;
			}
			for (int i = 0; i < 9; ++i) {
				if (lines[i].length() < 9) {
					System.out.println("Line length<9 line=" + lines[i]);
					return;
				}
				for (int j = 0; j < 9; ++j) {
					MATRIX[i][j] = Integer.parseInt(lines[i].substring(j, j + 1));
					MATRIX_ORIGINAL[i][j] = Integer.parseInt(lines[i].substring(j, j + 1));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printMatrix() {
		for (int i = 0; i < 9; ++i) {
			for (int j = 0; j < 9; ++j) {
				System.out.print(MATRIX[i][j]);
			}
			System.out.println();
		}
	}

	private static boolean check(int i, int j) {
		// horizontal
		Set<Integer> set = new HashSet<Integer>();
		for (int h = 0; h < 9; ++h) {
			if (MATRIX[i][h] == 0) {
				continue;
			}
			if (set.contains(MATRIX[i][h])) {
				return false;
			}
			set.add(MATRIX[i][h]);
		}

		// vertical
		set = new HashSet<Integer>();
		for (int h = 0; h < 9; ++h) {
			if (MATRIX[h][j] == 0) {
				continue;
			}
			if (set.contains(MATRIX[h][j])) {
				return false;
			}
			set.add(MATRIX[h][j]);
		}

		// square
		int startI = i - (i % 3);
		int startJ = j - (j % 3);
		set = new HashSet<Integer>();
		for (int h = startI; h < startI + 3; ++h) {
			for (int g = startJ; g < startJ + 3; ++g) {
				if (MATRIX[h][g] == 0) {
					continue;
				}
				if (set.contains(MATRIX[h][g])) {
					return false;
				}
				set.add(MATRIX[h][g]);
			}
		}
		return true;
	}

	private static void recursive(int i, int j) {
		if (MATRIX[i][j] == 0) {
			for (int k = 1; k <= 9; ++k) {
				MATRIX[i][j] = k;
				if (check(i, j)) {
					if (j < 8) {
						recursive(i, j + 1);
					} else if (i < 8) {
						recursive(i + 1, 0);
					} else {
						printMatrix();
						System.exit(0);
					}
				}
			}
			MATRIX[i][j] = 0;
		} else {
			if (j < 8) {
				recursive(i, j + 1);
			} else if (i < 8) {
				recursive(i + 1, 0);
			} else {
				printMatrix();
				System.exit(0);
			}
		}
	}

	public static void main(String[] args) {
		loadMatrix("matrix/matrix_1.txt");
		recursive(0, 0);
		System.out.println("ENDDDDD!!!!!!!!!");
	}
}
