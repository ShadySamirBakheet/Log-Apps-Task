package shady.samir.logappstask.DesingTask.ui.Courses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import shady.samir.logappstask.R


class CourseViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    val imagelist = MutableLiveData<List<DataSlider>>().apply {
        val list:ArrayList<DataSlider> = ArrayList()
        list.add(DataSlider(R.drawable.image,"Andriod Developer","Kotlin",4,4.589f,5.589f,125648))
        list.add(DataSlider(R.drawable.image,"Andriod Developer2","Kotlin",3,4.589f,5.589f,125648))
        list.add(DataSlider(R.drawable.image,"Andriod Developer3","Kotlin",5,4.589f,5.589f,125648))
        list.add(DataSlider(R.drawable.image,"Andriod Developer4","Kotlin",2,4.589f,5.589f,125648))
        list.add(DataSlider(R.drawable.image,"Andriod Developer5","Kotlin",4,4.589f,5.589f,125648))
        list.add(DataSlider(R.drawable.image,"Andriod Developer6","Kotlin",1,4.589f,5.589f,125648))
        list.add(DataSlider(R.drawable.image,"Andriod Developer7","Kotlin",5,4.589f,5.589f,125648))
        list.add(DataSlider(R.drawable.image,"Andriod Developer8","Kotlin",0,4.589f,5.589f,125648))

        value = list.toList()
    }

    val newlist = MutableLiveData<List<DataSlider>>().apply {
        val list:ArrayList<DataSlider> = ArrayList()
        list.add(DataSlider(R.drawable.image2,"Andriod Developer","Kotlin",4,4.589f,5.589f,125648))
        list.add(DataSlider(R.drawable.image2,"Andriod Developer2","Kotlin",3,4.589f,5.589f,125648))
        list.add(DataSlider(R.drawable.image2,"Andriod Developer3","Kotlin",5,4.589f,5.589f,125648))
        list.add(DataSlider(R.drawable.image2,"Andriod Developer4","Kotlin",2,4.589f,5.589f,125648))
        list.add(DataSlider(R.drawable.image2,"Andriod Developer5","Kotlin",4,4.589f,5.589f,125648))
        list.add(DataSlider(R.drawable.image2,"Andriod Developer6","Kotlin",1,4.589f,5.589f,125648))
        list.add(DataSlider(R.drawable.image2,"Andriod Developer7","Kotlin",5,4.589f,5.589f,125648))
        list.add(DataSlider(R.drawable.image2,"Andriod Developer8","Kotlin",0,4.589f,5.589f,125648))

        value = list.toList()
    }

    val toplist = MutableLiveData<List<Int>>().apply {
        val list:ArrayList<Int> = ArrayList()
        list.add(R.drawable.kotlin)
        list.add(R.drawable.kotlin)
        list.add(R.drawable.kotlin)
        list.add(R.drawable.kotlin)
        list.add(R.drawable.kotlin)
        list.add(R.drawable.kotlin)
        list.add(R.drawable.kotlin)
        list.add(R.drawable.kotlin)
        list.add(R.drawable.kotlin)
        list.add(R.drawable.kotlin)
        list.add(R.drawable.kotlin)
        list.add(R.drawable.kotlin)

        value = list.toList()
    }
}