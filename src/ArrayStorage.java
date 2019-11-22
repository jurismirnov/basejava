import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int index=0; //shows the position of first null

    //clear all values in storage (change to null)
    void clear() {
        for(int i=0;i<index;i++){ storage[i]=null;
        }
        index=0;
    }

    //put resume in the storage
    void save(Resume r) {
        //here could we check that index do not exceed the array length "if index<9999" or "try catch"
        storage[index]= r;
        index++;
    }

    //returns the resume by uuid
    Resume get(String uuid) {
        for(int i=0;i<index;i++){
            if(storage[i].uuid.equals(uuid)){
                return storage[i];
            }
        }
        return null;
    }

    //deletes the resume by uuid
    void delete(String uuid) {
        for(int i=0;i<index;i++){
            if(storage[i].uuid.equals(uuid)){
                storage[i]=storage[index-1];
                storage[index-1]=null;
                index--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        //Resume[] tmpStorage = new Resume[index];
        //for(int i=0;i<index;i++){
        //   tmpStorage[i]=storage[i];
        //}
        return Arrays.copyOfRange(storage,0, index);
    }

    int size() {
        return index;
    }
}
