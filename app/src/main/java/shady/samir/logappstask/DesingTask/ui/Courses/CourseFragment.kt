package shady.samir.logappstask.DesingTask.ui.Courses

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import shady.samir.logappstask.R

class CourseFragment : Fragment() {

    private lateinit var courseViewModel : CourseViewModel

    lateinit var imageSlider: SliderView
    lateinit var newest:RecyclerView
    lateinit var pervbtn:ImageView
    lateinit var nextbtn:ImageView
    lateinit var top:RecyclerView

    lateinit var adapter:PagerAdapter

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        courseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_courses, container, false)

        imageSlider = root.findViewById(R.id.imageSlider)
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setIndicatorSelectedColor(Color.WHITE);
        imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
        imageSlider.startAutoCycle();

        newest = root.findViewById(R.id.newest)
        top = root.findViewById(R.id.top)

        newest.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        newest.layoutManager = layoutManager

        top.setHasFixedSize(true)
        val layoutManager2 = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        top.layoutManager = layoutManager2

        nextbtn = root.findViewById(R.id.nextbtn)
        pervbtn = root.findViewById(R.id.pervbtn)

        courseViewModel.imagelist.observe(this, Observer {

            imageSlider.setSliderAdapter(SliderAdapter(it,context));

            pervbtn.setOnClickListener {
                imageSlider.currentPagePosition =  imageSlider.currentPagePosition - 1
            }

            nextbtn.setOnClickListener {
                imageSlider.currentPagePosition =  imageSlider.currentPagePosition + 1
            }

        })

        courseViewModel.newlist.observe(this, Observer {
            newest.adapter = DataCourseAdapter(context,it)
        })

        courseViewModel.toplist.observe(this, Observer {
            top.adapter = TopAdapter(context,it)
        })

        return root
    }

}