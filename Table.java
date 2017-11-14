public class Table {

	public static int[] get(int shape) {
		return map.get(shape);
	}

	private static final HashMap<Integer, int[]> map = new HashMap<>();

	static {
		initialize();
	}

	private static void init0() {
		map.put(1,2);
		map.put(2,3);
		map.put(3,4);
		map.put(3,4);
		map.put(3,4);
		map.put(3,4);
		map.put(3,4);
		map.put(3,4);
		map.put(3,4);
		map.put(3,4);
		map.put(3,4);
		map.put(3,4);
		map.put(3,4);
		map.put(3,4);
		map.put(3,4);
		map.put(3,4);
		map.put(3,4);
		map.put(3,4);
		map.put(3,4);
	}

	private static void initialize(){
		init0();
	}
}
