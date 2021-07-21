import java.util.Arrays;
import java.util.Iterator;

public class LlistaPAED<E> implements Iterable<E> {
    private int size = 0;
    private static final int TAMANY_PER_DEFECTE = 30;
    private Object elements[];

    public LlistaPAED() {
        elements = new Object[TAMANY_PER_DEFECTE];
    }


		@Override
	 public Iterator<E> iterator() {
			 Iterator<E> it = new Iterator<E>() {

					 private int currentIndex = 0;

					 @Override
					 public boolean hasNext() {
							 return currentIndex < size && elements[currentIndex] != null;
					 }

					 @Override
					 public E next() {
							 return (E) elements[currentIndex++];
					 }

					 @Override
					 public void remove() {
							 throw new UnsupportedOperationException();
					 }
			 };
			 return it;
	 }

    public static LlistaPAED listFromArray(Object array[]){
        LlistaPAED a  = new LlistaPAED();
        for(int i=0; i<array.length ;i++){
            a.add(array[i]);
        }
        return a;
    }

    private void adaptarTamany() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }

    public void add(E e) {
        if (size == elements.length) {
            adaptarTamany();
        }
        elements[size++] = e;
    }

		public void add(int index, E e) {
        if (index < elements.length) {
            elements[index] = e;
        }
    }

    @SuppressWarnings("unchecked")
    public E get(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + i);
        }
        return (E) elements[i];
    }

    public void remove(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + i);
        }
        Object item = elements[i];
        int nouTotal = elements.length - ( i + 1 ) ;
        System.arraycopy( elements, i + 1, elements, i, nouTotal ) ;
        size--;
    }

		public void remove(E k) {
        for(int i=0; i<size ;i++){
            if(elements[i] == k){
                remove(i);
            }
        }
    }

		public void removeAll(){
			for(int j = size - 1; j >= 0; j--){
				remove(j);
			}
		}


	public E getRemoveFirst(){
        E  obj = get(0);
        remove(0);
        return obj;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public void addAll(LlistaPAED<E> list){
			for(int i= 0; i<list.size(); i++){
				add(list.get(i));
			}
    }

}
