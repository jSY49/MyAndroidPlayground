package com.example.apod.network

import com.example.apod.data.ApodResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApi {

    //https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY
    /*Parameter	Type	Default	Description
    date	YYYY-MM-DD	today	The date of the APOD image to retrieve
    start_date	YYYY-MM-DD	none	The start of a date range, when requesting date for a range of dates. Cannot be used with date.
    end_date	YYYY-MM-DD	today	The end of the date range, when used with start_date.
    count	int	none	If this is specified then count randomly chosen images will be returned. Cannot be used with date or start_date and end_date.
    thumbs	bool	False	Return the URL of video thumbnail. If an APOD is not a video, this parameter is ignored.
    api_key	string	DEMO_KEY	api.nasa.gov key for expanded usage

    https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&start_date=2026-04-09&end_date=2026-04-13
    [
    {
    "copyright": "José Rodrigues",
    "date": "2026-04-12",
    "explanation": "Comet R3 is brightening rapidly -- will it survive?  C/2025 R3 (PanSTARRS) has been slowly brightening and extending an ion tail since its discovery last year.  This shedding mountain of dirty ice puts on its best sky show this month, though, because it passes its closest to both the Sun (April 19) and the Earth (April 25).  The featured image, showing R3 already sporting a tail extending over 10 degrees, was taken two nights ago from Sion, Switzerland with the big mountain Bietschhorn on the left.  Comet R3 will be visible during mid-April before sunrise. Although the future brightness of any comet is hard to predict, the brightness of R3 makes it already a good camera comet and it may become visible to the unaided eye in the next week.  Comet R3's physical future is also unknown because, like Comet A1 (MAPS) earlier this month, it may disintegrate when it passes its closest to the Sun.  Or it may live to leave the Solar System.    Growing Gallery: Comet R3 in 2026",
    "hdurl": "https://apod.nasa.gov/apod/image/2604/R3Panstarrs_Rodrigues_1707.jpg",
    "media_type": "image",
    "service_version": "v1",
    "title": "Comet R3 (PanSTARRS) Brightens",
    "url": "https://apod.nasa.gov/apod/image/2604/R3Panstarrs_Rodrigues_960.jpg"
    },
    ]
    */
    @GET("planetary/apod")
    suspend fun getApod(@Query("api_key") apiKey: String): ApodResponse
}