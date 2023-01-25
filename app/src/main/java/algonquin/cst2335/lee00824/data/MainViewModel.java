package algonquin.cst2335.lee00824.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public MutableLiveData<String> editString = new MutableLiveData<>();
    public MutableLiveData<Boolean> buttonSelected = new MutableLiveData<>();

}
