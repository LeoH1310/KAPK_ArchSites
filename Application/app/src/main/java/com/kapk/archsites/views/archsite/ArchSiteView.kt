package com.kapk.archsites.views.archsite

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.kapk.archsites.R
import com.kapk.archsites.models.ArchSiteModel
import com.kapk.archsites.views.BaseView
import kotlinx.android.synthetic.main.activity_archsite.*
import kotlinx.android.synthetic.main.activity_archsite.toolbar
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast

class ArchSiteView : BaseView(), AnkoLogger {

    lateinit var presenter: ArchSitePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archsite)
        init(toolbar, true)
        btn_addImage.setOnClickListener { presenter.doSelectImage() }

        presenter = initPresenter (ArchSitePresenter(this)) as ArchSitePresenter
    }

    override fun showArchSite(archSite: ArchSiteModel) {
        txt_ArchSite_Name.setText(archSite.name)
        txt_ArchSite_Description.setText(archSite.description)
        check_ArchSite_visited.isChecked = archSite.visited
        setImageSlider(archSite)

        //if (placemark.image != null) {
            //chooseImage.setText(R.string.change_placemark_image)
        //}
        //this.showLocation(placemark.location)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.archsite_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_delete -> {
                presenter.doDelete()
            }
            R.id.menu_item_save -> {
                if (txt_ArchSite_Name.text.toString().isEmpty()) {
                    toast(R.string.toast_enterName)
                } else {
                    presenter.doAddOrSave(txt_ArchSite_Name.text.toString(), txt_ArchSite_Description.text.toString(), check_ArchSite_visited.isChecked)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setImageSlider(archSite: ArchSiteModel){
        val imageList = ArrayList<SlideModel>()
        // imageList.add(SlideModel("String Url" or R.drawable)
        // imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title
        // imageList.add(SlideModel("String Url" or R.drawable, "title", true) Also you can add centerCrop scaleType for this image

        if (archSite.images[0] == ""){
            imageList.add(
                SlideModel(
                    R.mipmap.ic_launcher,true
                )
            )
        }
        else {
            for (x in 0 until archSite.images.size){
                if (archSite.images[x] != "") {
                    imageList.add(
                        SlideModel(
                            archSite.images[x], true
                        )
                    )
                }
            }
        }

        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
        imageSlider.stopSliding()
    }
}