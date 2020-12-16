package shady.samir.logappstask.DesingTask.ui.Courses


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.smarteist.autoimageslider.SliderViewAdapter
import shady.samir.logappstask.R


class SliderAdapter(list: List<DataSlider>,context: Context?): SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {
    private var context: Context? = null
    private var mSliderItems: List<DataSlider> = ArrayList()

    init {
        this.context = context
        this.mSliderItems = list
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.slider_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val dataSlider: DataSlider = mSliderItems[position]

        viewHolder.apply {
            image_course.setImageResource(dataSlider.imageid)
            numView.text = "( "+dataSlider.numView+" )"
            newSalary.text = "EGP "+dataSlider.newSalary
            oldSalary.text = "EGP "+dataSlider.oldSalary
            course_title.text=dataSlider.title
            course_subtitle.text=dataSlider.subtitle
            when(dataSlider.star){
                1 -> {
                    star1.setImageResource(R.drawable.ic_isstart)
                    star2.setImageResource(R.drawable.ic_start)
                    star3.setImageResource(R.drawable.ic_start)
                    star4.setImageResource(R.drawable.ic_start)
                    star5.setImageResource(R.drawable.ic_start)
                }
                2 -> {
                    star1.setImageResource(R.drawable.ic_isstart)
                    star2.setImageResource(R.drawable.ic_isstart)
                    star3.setImageResource(R.drawable.ic_start)
                    star4.setImageResource(R.drawable.ic_start)
                    star5.setImageResource(R.drawable.ic_start)
                }
                3 -> {
                    star1.setImageResource(R.drawable.ic_isstart)
                    star2.setImageResource(R.drawable.ic_isstart)
                    star3.setImageResource(R.drawable.ic_isstart)
                    star4.setImageResource(R.drawable.ic_start)
                    star5.setImageResource(R.drawable.ic_start)
                }
                4 -> {
                    star1.setImageResource(R.drawable.ic_isstart)
                    star2.setImageResource(R.drawable.ic_isstart)
                    star3.setImageResource(R.drawable.ic_isstart)
                    star4.setImageResource(R.drawable.ic_isstart)
                    star5.setImageResource(R.drawable.ic_start)
                }
                5 -> {
                    star1.setImageResource(R.drawable.ic_isstart)
                    star2.setImageResource(R.drawable.ic_isstart)
                    star3.setImageResource(R.drawable.ic_isstart)
                    star4.setImageResource(R.drawable.ic_isstart)
                    star5.setImageResource(R.drawable.ic_isstart)
                }
                else -> {
                    star1.setImageResource(R.drawable.ic_start)
                    star2.setImageResource(R.drawable.ic_start)
                    star3.setImageResource(R.drawable.ic_start)
                    star4.setImageResource(R.drawable.ic_start)
                    star5.setImageResource(R.drawable.ic_start)
                }

            }

        }

    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mSliderItems.size
    }

    inner class SliderAdapterVH(view: View) : ViewHolder(view) {
        val image_course: ImageView
        val star1: ImageView
        val star2: ImageView
        val star3: ImageView
        val star4: ImageView
        val star5: ImageView
        val numView:TextView
        val newSalary:TextView
        val oldSalary:TextView
        val course_title:TextView
        val course_subtitle:TextView

        init {
            image_course = view.findViewById(shady.samir.logappstask.R.id.image_course)
            star1 = view.findViewById(shady.samir.logappstask.R.id.star1)
            star2 = view.findViewById(shady.samir.logappstask.R.id.star2)
            star3 = view.findViewById(shady.samir.logappstask.R.id.star3)
            star4 = view.findViewById(shady.samir.logappstask.R.id.star4)
            star5 = view.findViewById(shady.samir.logappstask.R.id.star5)
            numView = view.findViewById(shady.samir.logappstask.R.id.numView)
            newSalary = view.findViewById(shady.samir.logappstask.R.id.newsalary)
            oldSalary = view.findViewById(shady.samir.logappstask.R.id.oldsalary)
            course_title = view.findViewById(shady.samir.logappstask.R.id.course_title)
            course_subtitle = view.findViewById(shady.samir.logappstask.R.id.course_subtitle)

        }
    }
}