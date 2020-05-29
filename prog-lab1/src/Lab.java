// Лабораторная работа №1
// Вариант 824728

public class Lab {
	public static void main(String[] args) {
		// Объявление и заполнение первого массива
		final int F_SIZE = 17;
		final int F_MAX = 20;
		short[] f = new short[F_SIZE];

		for (int i=0; i<F_SIZE; i++) {
			f[i] = (short) (F_MAX-i);
		}
		
		// Объявление и заполнение второго массива
		final int X_SIZE = 10;
		final float MIN_RANGE = -11.0f;
		final float MAX_RANGE = 2.0f; 
		float[] x = new float[X_SIZE];

		for (int i=0; i<X_SIZE; i++) {
			x[i] = (float) ((Math.random() * (MAX_RANGE-MIN_RANGE)) + MIN_RANGE);
		}

		// Объявление и заполнение третьего (итогового) массива
		final int T_FIRST_SIZE = 17;
		final int T_SECOND_SIZE = 10;
		double[][] t = new double[T_FIRST_SIZE][T_SECOND_SIZE];
		double cache;

		for (int i=0; i<T_FIRST_SIZE; i++) {
			for (int j=0; j<T_SECOND_SIZE; j++) {
				if (f[i] == 15) {
					cache = 2 / Math.pow(x[j], 1 - x[j]);
					cache = Math.pow(cache, 2);
					cache = Math.pow(cache * 2 / 3, 3);
				} else if (f[i] == 5 || f[i] == 6 || f[i] == 11 || f[i] == 13 ||
									 f[i] == 14 || f[i] == 17 || f[i] == 18 || f[i] == 20) {
					cache = Math.pow(x[j] / 3, x[j]);
					cache = Math.pow(cache, Math.asin((x[j] - 4.5) / 13));
					cache = Math.pow(cache, 2 * Math.sin(x[j]));
					cache = Math.pow(cache, (1 - Math.asin((x[j] - 4.5) / 13)) / 3);
				} else {
					cache = Math.pow(x[j] / (1-x[j]), 3);
					cache = Math.pow(Math.E, cache);
					cache = Math.pow(Math.E, cache);
					cache = Math.sqrt(cache);
					cache = 1 / Math.pow(Math.E, cache);
					cache = Math.asin(cache);
				}
				t[i][j] = cache;
			}
		}

		// Вывод третьего (итогового) массива
		for (int i=0; i<T_FIRST_SIZE; i++) {
			for (int j=0; j<T_SECOND_SIZE; j++) {
				System.out.printf("%.5f ", t[i][j]);
			}
			System.out.println();
		}
	}
}
