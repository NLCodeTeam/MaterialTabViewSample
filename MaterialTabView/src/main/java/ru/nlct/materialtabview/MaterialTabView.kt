package ru.nlct.materialtabview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButton

class MaterialTabView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
): LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private lateinit var firstTab: MaterialButton
    private lateinit var secondTab: MaterialButton

    private var backgroundFilled: ColorStateList? = null
    private var backgroundUnfilled: ColorStateList? = null
    private var drawableUnfilled: ColorStateList? = null

    private var listener: ((TAB) -> Unit)? = null
    //текущий таб
    private var currentTab = TAB.FIRST
    fun getCurrentTab(): TAB = currentTab

    enum class TAB {
        FIRST,
        SECOND
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.tab_first -> setActiveTab(TAB.FIRST)
            R.id.tab_second -> setActiveTab(TAB.SECOND)
        }
    }

    //метод который выделает кликнутую вьюху или дефолтную
    fun setActiveTab(tabId: TAB) {
        currentTab = tabId
        when (tabId) {
            TAB.FIRST -> {
                setTabsBackground(firstTab, secondTab)
                listener?.invoke(TAB.FIRST)
            }
            TAB.SECOND -> {
                setTabsBackground(secondTab, firstTab)
                listener?.invoke(TAB.SECOND)
            }
        }
    }

    //сеттер для колбека
    fun setOnMaterialTabViewClickListener(listener: (TAB) -> Unit) {
        this.listener = listener
    }

    init {
        createView(context)
        setParams(context, attrs)
    }

    constructor(context: Context) : this(context, null, 0) {
        createView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        createView(context)
        setParams(context, attrs)
    }

    private fun setParams(context: Context, attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.MaterialTabView, 0, 0)

        val tabBackground = typedArray.getDrawable(R.styleable.MaterialTabView_tabBackground)
        if (tabBackground != null)
            background = tabBackground

        val cornerRadius = typedArray.getDimension(
            R.styleable.MaterialTabView_cornerRadius,
            context.resources.getDimension(R.dimen.default_corner_radius)
        )

        val textFirstTab = typedArray.getText(R.styleable.MaterialTabView_textFirstTab)
        val iconFirstTab = typedArray.getDrawable(R.styleable.MaterialTabView_iconFirstTab)

        val iconSecondTab = typedArray.getDrawable(R.styleable.MaterialTabView_iconSecondTab)
        val textSecondTab = typedArray.getText(R.styleable.MaterialTabView_textSecondTab)

        firstTab.setParams(cornerRadius, textFirstTab, iconFirstTab)
        secondTab.setParams(cornerRadius, textSecondTab, iconSecondTab)

        backgroundFilled = typedArray.getColorStateList(R.styleable.MaterialTabView_backgroundFilledTint)
        backgroundUnfilled = typedArray.getColorStateList(R.styleable.MaterialTabView_backgroundUnfilledTint)
        drawableUnfilled = typedArray.getColorStateList(R.styleable.MaterialTabView_drawableUnfilledTint)
    }

    private fun MaterialButton.setParams(cornerRadius: Float, text: CharSequence, icon: Drawable?) {
        this.cornerRadius = cornerRadius.roundToInt()
        this.text = text
        if (icon != null) this.icon = icon
    }

    private fun Float.roundToInt() =
        if (this - this.toInt() != 0f)
            this.toInt() + 1
        else
            this.toInt()

    private fun createView(context: Context) {
        inflate(context, R.layout.tab_view, this)
        firstTab = findViewById(R.id.tab_first)
        secondTab = findViewById(R.id.tab_second)

        firstTab.setOnClickListener(this)
        secondTab.setOnClickListener(this)
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    private fun setTabsBackground(activeTab: MaterialButton, inactiveTab: MaterialButton) {
        with(activeTab) {
            setTextColor(backgroundUnfilled)
            backgroundTintList = backgroundFilled
            compoundDrawableTintList = backgroundUnfilled
        }
        with(inactiveTab) {
            setTextColor(drawableUnfilled)
            backgroundTintList = backgroundUnfilled
            compoundDrawableTintList = drawableUnfilled
        }
    }

}