package com.example.ticketgo.ui

import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.ticketgo.R
import com.example.ticketgo.base.BaseActivity
import com.example.ticketgo.databinding.ActivityMainBinding
import com.example.ticketgo.ui.event_type.EventCategory
import com.example.ticketgo.ui.event_type.EventType
import com.example.ticketgo.ui.events.Event
import com.example.ticketgo.utils.DateTimeUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun initView() {
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initData() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navigationController = navHostFragment.navController
        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            navigationController
        )

        navigationController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentEvents -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.fragmentsYourBookings -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.fragmentAccount -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
                else -> binding.bottomNavigationView.visibility = View.GONE
            }
        }

        addAllEventCategories()
        addAllEvents()
    }

    override fun initListener() {

    }

    private fun addAllEventCategories() {
        viewModel.addEventCategory(eventCategoryList = getEventCategories())
    }

    private fun addAllEvents() {
        viewModel.addEvents(getEventList())
    }

    private fun getEventCategories() = arrayOf(
        EventType(EventCategory.All.name, "All"),
        EventType(EventCategory.COMEDY.name, "Comedy"),
        EventType(EventCategory.PLAY.name, "Play"),
        EventType(EventCategory.MOVIE.name, "Movie")
    )

    private fun getEventList() = arrayOf(
        Event(
            eventId = 1,
            eventName = "Wednesday",
            eventDescription = "Follows Wednesday Addams' years as a student, when she attempts to master her emerging psychic ability, thwart and solve the mystery that embroiled her parents.",
            bannerImageUrl = "https://upload.wikimedia.org/wikipedia/en/6/66/Wednesday_Netflix_series_poster.png",
            eventDurationSecond = 3600,
            startTime = DateTimeUtils.getNextDayTime(hourOfDay = 10),
            eventCategory = EventCategory.COMEDY.name
        ),
        Event(
            eventId = 2,
            eventName = "The White Lotus",
            eventDescription = "Set in a tropical resort, it follows the exploits of various guests and employees over the span of a week",
            bannerImageUrl = "https://m.media-amazon.com/images/M/MV5BYjdjNzBmYjEtM2Y5My00YjI0LWJjY2YtOGQ4MjNkNmE2MDVjXkEyXkFqcGdeQXVyMTEyMjM2NDc2._V1_FMjpg_UX1000_.jpg",
            eventDurationSecond = 3600,
            startTime = DateTimeUtils.getNextDayTime(hourOfDay = 11),
            eventCategory = EventCategory.COMEDY.name
        ),
        Event(
            eventId = 3,
            eventName = "The Santa Clause",
            eventDescription = "Scott Calvin is about to turn 65 and, realizing he can't be Santa forever, sets out to find a suitable replacement Santa while preparing his family for a new adventure in life south of the pole.",
            bannerImageUrl = "https://m.media-amazon.com/images/M/MV5BYjkwZGM3NjQtNDM0ZS00Yjg3LTg1ODgtYWE3ZmNmNDBjMDI1XkEyXkFqcGdeQXVyMTM1MTE1NDMx._V1_.jpg",
            eventDurationSecond = 3600,
            startTime = DateTimeUtils.getNextDayTime(hourOfDay = 12),
            eventCategory = EventCategory.COMEDY.name
        ), Event(
            eventId = 4,
            eventName = "Yayati",
            eventDescription = "Yayati, Girish Karnard's first play, was written in 1960 and won the Mysore State Award in 1962. It is based on an episode in the Mahabharata, where Yayati, one of the ancestors of the Pandavas, is given the curse of premature old age by his father-in-law, Shukracharya, who is incensed by Yayati's infidelity.",
            bannerImageUrl = "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSoDY2drCDhHFg2HFp68k7HteKR_5eqA_7aMY2pV9ez0XNAf4xD",
            eventDurationSecond = 3600,
            startTime = DateTimeUtils.getNextDayTime(hourOfDay = 13),
            eventCategory = EventCategory.PLAY.name
        ), Event(
            eventId = 5,
            eventName = "Andher Nagari",
            eventDescription = "Andher Nagri is a play written by Indian Hindi writer Bhartendu Harishchandra in 1881. In this 6-act play, with satire, he has shown a king destroyed by his own deeds by an irrational and autocratic government system. Bhartendu composed it in a single day for the Hindu National Theater in Banaras.",
            bannerImageUrl = "https://rukminim1.flixcart.com/image/416/416/kfbfr0w0/book/2/8/7/andher-nagari-original-imafvsxfzvbjghg6.jpeg?q=70",
            eventDurationSecond = 3600,
            startTime = DateTimeUtils.getNextDayTime(hourOfDay = 14),
            eventCategory = EventCategory.PLAY.name
        ), Event(
            eventId = 6,
            eventName = "Hamlet",
            eventDescription = "The Tragedy of Hamlet, Prince of Denmark, often shortened to Hamlet, is a tragedy written by William Shakespeare sometime between 1599 and 1601. It is Shakespeare's longest play, with 29,551 words.",
            bannerImageUrl = "https://m.media-amazon.com/images/I/51A+CQpR2vL.jpg",
            eventDurationSecond = 3600,
            startTime = DateTimeUtils.getNextDayTime(hourOfDay = 15),
            eventCategory = EventCategory.PLAY.name
        ), Event(
            eventId = 7,
            eventName = "Black Panther: Wakanda Forever",
            eventDescription = "The people of Wakanda fight to protect their home from intervening world powers as they mourn the death of King T'Challa.",
            bannerImageUrl = "https://m.media-amazon.com/images/M/MV5BMTg1MTY2MjYzNV5BMl5BanBnXkFtZTgwMTc4NTMwNDI@._V1_.jpg",
            eventDurationSecond = 3600,
            startTime = DateTimeUtils.getNextDayTime(hourOfDay = 16),
            eventCategory = EventCategory.MOVIE.name
        ), Event(
            eventId = 8,
            eventName = "The Guardians of the Galaxy: Holiday Special",
            eventDescription = "Star-Lord, Drax, Rocket, Mantis, and Groot engage in some spirited shenanigans in an all-new original special created for Disney+.",
            bannerImageUrl = "https://m.media-amazon.com/images/M/MV5BOGJjMzlmNzctMWI4Yi00MjcyLWFmYzAtN2JmZjU0YTM4YmRmXkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_FMjpg_UX1000_.jpg",
            eventDurationSecond = 3600,
            startTime = DateTimeUtils.getNextDayTime(hourOfDay = 17),
            eventCategory = EventCategory.MOVIE.name
        )
    )
}