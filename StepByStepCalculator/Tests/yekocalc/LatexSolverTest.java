package yekocalc;

public class LatexSolverTest {
		static LatexSolver solver = new LatexSolver();
		
		public static void main(String[] args) {
			long t1 = System.nanoTime();
			System.out.println(solver.userStringToLatex("sqrt"));
			long t2 = System.nanoTime();
			System.out.println((t2-t1)/1000000000.0);
			
			System.out.println(solver.solveUserLatex("2(x+8)(x-4)=x^2"));
		}
}
