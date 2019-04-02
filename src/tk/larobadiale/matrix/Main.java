package tk.larobadiale.matrix;

public class Main {

	public static void main(String[] args) {
		float[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 8}};
		
		try {
			System.out.println("La determinante è: " + det(matrix, 3) + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Real re = new Real(3, 5, 1, 1);
		System.out.println("Re1: " + re.toString() + " = " + re.toDouble());
		Real re2 = new Real(1, 1, 1105920, 80);
		System.out.println("Re2: " + re2.toString() + " = " + re2.toDouble());
		
		Real re3 = re.div(re2);
		System.out.println("Re1 * Re2 = " + re3.toString() + " = " + re3.toDouble());
		//System.out.println("Do1 * Do2 = " + ((double)3/5) + " * " + (double)(2*Math.sqrt(2)) + " = " + ((double)(3/5)*(2*Math.sqrt(2))));
	}
	
	public static float det(float[][] mat, int dim) throws Exception {
		//Real res = new Real(0);
		float res = 0;
		int flag = 0;
		
		//TODO Usare Real al posto di float
		/* TODO Migliorare il sistema di controllo che sia quadrata e della dimensione corretta.
		try {
			mat[dim][0] *= 1;
		} catch (Exception e) {
			flag++;
		}
		
		try {
			mat[0][dim] *= 1;
		} catch (Exception e) {
			flag++;
		}*/
		
		if (flag > 0) throw new Exception("Matrix should be a square.");
		
		for (int i = 0; i < dim; i++) {
			float mult = 1;
			float mult2 = 1;
			for (int j = 0; j < dim; j++) {
				int n = (i+j) % dim;
				mult  *= mat[j][n];
				mult2 *= -mat[dim-j-1][n];
			}
			res += (mult + mult2);
		}
		
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println("");
		}
		
		return res;
	}

}
