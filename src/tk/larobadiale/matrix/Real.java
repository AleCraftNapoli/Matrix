package tk.larobadiale.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class Real implements Primes {
	private boolean mFlag;
	private int mNum, mDen, mRad, mDRad;
	private ArrayList<Real> mSums = new ArrayList<>();

	public Real(int n) {
		this(n, 1);
	}

	public Real(int n, int d) {
		this(n, d, 1);
	}

	public Real(int n, int d, int r) {
		this(n, d, r, 1);
	}

	public Real(int n, int d, int r, int dr) {
		this(n, d, r, dr, null);
	}

	protected Real(int n, int d, int r, int dr, boolean b) {
		this(n, d, r, dr, null, b);
	}

	protected Real(int n, int d, int r, int dr, ArrayList<Real> sums, boolean b) {
		this(n, d, r, dr, sums);
		this.mFlag = true;
	}

	public Real(int n, int d, int r, int dr, ArrayList<Real> sums) {
		this.mNum = n;
		this.mDen = d;
		this.mRad = r;
		this.mDRad = dr;
		if (sums != null)
			this.mSums = sums;
		else
			this.mSums = new ArrayList<Real>();
		this.reduce();
	}

	private void reduce() {
		int v = MCD(mNum, mDen);
		mNum /= v;
		mDen /= v;

		v = MCD(mRad, mDRad);
		mRad /= v;
		mDRad /= v;

		if (Math.sqrt(mRad) == Math.floor(Math.sqrt(mRad))) {
			mNum *= Math.sqrt(mRad);
			mRad = 1;
		}

		if (Math.sqrt(mDRad) == Math.floor(Math.sqrt(mDRad))) {
			mDen *= Math.sqrt(mDRad);
			mDRad = 1;
		}

		HashMap<Integer, Integer> pr1 = factors(mRad);
		HashMap<Integer, Integer> pr2 = factors(mDRad);
		
		BiConsumer<Integer, Integer> reduce = new BiConsumer<Integer, Integer>() {
			@Override
			public void accept(Integer p, Integer n) {
				if (n > 1) {
					int s = 2*(n/2);
					
					mNum *= Math.pow(p, s/2);
					mRad /= Math.pow(p, s);
				}
			}
		};
		
		pr1.forEach(reduce);
		pr2.forEach(reduce);

		if (mFlag) mSums = null;
	}

	// ADDIZIONE
	public Real add(int n) {
		return this.add(new Real(n));
	}

	public Real add(int n, int d) {
		return this.add(new Real(n, d));
	}

	public Real add(int n, int d, int r) {
		return this.add(new Real(n, d, r));
	}

	public Real add(Real r) {
		Real z = new Real(0);
		if (this.mRad == r.mRad && this.mDRad == r.mDRad) {
			z.mNum = this.mNum * r.mDen + r.mNum * this.mDen;
			z.mDen = this.mDen * r.mDen;
			z.mRad = this.mRad;
		} else {
			z.mNum = this.mNum;
			z.mDen = this.mDen;
			z.mRad = this.mRad;
			z.mSums.add(new Real(r.mNum, r.mDen, r.mRad, r.mDRad, true));
		}

		z.reduce();
		return z;
	}

	// SOTTRAZIONE
	public Real sub(int n) {
		return this.sub(new Real(n));
	}

	public Real sub(int n, int d) {
		return this.sub(new Real(n, d));
	}

	public Real sub(int n, int d, int r) {
		return this.sub(new Real(n, d, r));
	}

	public Real sub(Real r) {
		return this.add(r.opposite());
	}

	// MOLTIPLICAZIONE
	public Real mult(int n) {
		return this.sub(new Real(n));
	}

	public Real mult(int n, int d) {
		return this.sub(new Real(n, d));
	}

	public Real mult(int n, int d, int r) {
		return this.sub(new Real(n, d, r));
	}

	public Real mult(Real r) {
		Real z = new Real(0);

		z.mNum = this.mNum * r.mNum;
		z.mDen = this.mDen * r.mDen;
		z.mRad = this.mRad * r.mRad;
		z.mDRad = this.mDRad * r.mDRad;
		// TODO Mult more factors
		return z;
	}

	// DIVISIONE
	public Real div(int n) {
		return this.sub(new Real(n));
	}

	public Real div(int n, int d) {
		return this.sub(new Real(n, d));
	}

	public Real div(int n, int d, int r) {
		return this.sub(new Real(n, d, r));
	}

	public Real div(Real r) {
		return this.mult(r.reciprocal());
	}

	// MATHS
	public Real opposite() {
		return new Real(-this.mNum, this.mDen, this.mRad, this.mDRad, this.mSums);
	}

	public Real reciprocal() {
		return new Real(this.mDen, this.mNum, this.mDRad, this.mRad, this.mSums);
	}

	private static int MCD(int a, int b) {
		return b == 0 ? a : MCD(b, a % b);
	}

	private static HashMap<Integer, Integer> factors(int n) { 
		ArrayList<Integer> list = factors(n, 0);
		
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int a : list) {
			if (!map.containsKey(a)) {
				map.put(a, 1);
			}
			else {
				map.put(a, map.get(a)+1);
			}
		}
		
		return map;
	}
	
	private static ArrayList<Integer> factors(int n, int index) {
		ArrayList<Integer> list = new ArrayList<>();
		int prime = PRIMES[index];
				
		if ((n % prime) == 0) {
			list.add(prime);
			list.addAll(factors(n/prime, index));
		}
		else if (n > prime) {
			list.addAll(factors(n, ++index));
		}
		
		return list;
	}

	/// VARIABLES AND OUTPUT
	public int getNum() {
		return mNum;
	}

	public int getDen() {
		return mDen;
	}

	public int getRad() {
		return mRad;
	}

	public double toDouble() {
		return (double) mNum / mDen * Math.sqrt(mRad) / Math.sqrt(mDRad);
	}

	public float toFloat() {
		return ((float) this.toDouble());
	}

	@Override
	public String toString() {
		return mNum + "/" + mDen + " * sqrt(" + mRad + "/" + mDRad + ")";
	}

}
