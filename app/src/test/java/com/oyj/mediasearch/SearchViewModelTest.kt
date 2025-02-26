package com.oyj.mediasearch

import com.oyj.mediasearch.data.model.MediaImage
import com.oyj.mediasearch.data.model.MediaVideo
import com.oyj.mediasearch.data.repository.MediaRepository
import com.oyj.mediasearch.data.repository.MediaRepositoryImpl
import com.oyj.mediasearch.data.repository.remote.MediaRemoteSource
import com.oyj.mediasearch.repository.remote.FakeMediaRemoteSourceImpl
import com.oyj.mediasearch.ui.search.SearchViewModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class SearchViewModelTest {
    private lateinit var mediaRemoteSource: MediaRemoteSource
    private lateinit var mediaRepository: MediaRepository
    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun init() {
        mediaRemoteSource = FakeMediaRemoteSourceImpl()
        mediaRepository = MediaRepositoryImpl(
            mediaRemoteSource = mediaRemoteSource
        )
        searchViewModel = SearchViewModel(
            repository = mediaRepository
        )
    }

    @Test
    fun `사용자_검색어_입력_테스트`() {
        // Given : 사용자가 검색어를 입력한다.
        val keyword = "노머스"

        // When : 사용자가 검색어를 입력하면 쿼리를 세팅을 한다
        searchViewModel.setQuery(keyword)

        // Then : 검색어가 세팅되어야 한다.
        assertThat(searchViewModel.query.value).isEqualTo(keyword)
    }

    @Test
    fun `searchImage()_성공_케이스`() {
        // Given : 사용자가 입력한 검색어를 세팅한다.
        val keyword = "노머스"
        searchViewModel.setQuery(keyword)

        // When : 검색어를 기반으로 사진 데이터 호출을 한다.
        searchViewModel.searchImage()
        val expect = listOf(
            MediaImage(
                thumbnail = "https://search1.kakaocdn.net/argon/130x130_85_c/59SRPcaiRjj",
                dateTime = "2024-09-30T11:00:12.000+09:00",
                mediaUrl = "https://t1.daumcdn.net/news/202410/02/moneytoday/20241002145139012fjsm.jpg",
                sources = "머니투데이",
                imgUrl = "https://t1.daumcdn.net/news/202410/02/moneytoday/20241002145139012fjsm.jpg"
            ),
            MediaImage(
                thumbnail = "https://search3.kakaocdn.net/argon/130x130_85_c/JnWUPSerL3k",
                dateTime = "2023-08-29T02:59:34.000+09:00",
                mediaUrl = "https://t1.daumcdn.net/news/202308/29/sportskhan/20230829025936511wlwd.jpg",
                sources = "스포츠경향",
                imgUrl = "https://t1.daumcdn.net/news/202308/29/sportskhan/20230829025936511wlwd.jpg"
            ),
            MediaImage(
                thumbnail = "https://search2.kakaocdn.net/argon/130x130_85_c/6YrVdOGwMLf",
                dateTime = "2024-11-06T06:01:24.000+09:00",
                mediaUrl = "https://blog.kakaocdn.net/dn/dvudoW/btsKw3ypCL2/r9P85fBYr1vfIhOWrFg6m1/img.jpg",
                sources = "티스토리",
                imgUrl = "https://blog.kakaocdn.net/dn/dvudoW/btsKw3ypCL2/r9P85fBYr1vfIhOWrFg6m1/img.jpg"
            )
        )

        // Then : 이미지 데이터가 저장된다.
        val actual = searchViewModel.imageList.value
        for (i in 0 until 3) {
            assertThat(actual[i]).isEqualTo(expect[i])
        }
    }

    @Test
    fun `searchVideo_성공_케이스`() {
        // Given : 사용자가 입력한 검색어를 세팅한다.
        val keyword = "노머스"
        searchViewModel.setQuery(keyword)

        // When : 검색어를 기반으로 비디오 데이터 호출을 한다.
        searchViewModel.searchVideo()

        val expect = listOf(
            MediaVideo(
                thumbnail = "https://search3.kakaocdn.net/argon/138x78_80_pr/GO8K4K8FeMw",
                dateTime = "2025-01-21T07:00:03.000+09:00",
                mediaUrl = "http://www.youtube.com/watch?v=5bT529iPigg",
                sources = "SBS Biz 증권",
                title = "글로벌 엔터기업 노머스! 한파 딛고 반등 시작?",
                playTime = 122
            ),
            MediaVideo(
                thumbnail = "https://search2.kakaocdn.net/argon/138x78_80_pr/K2SoA4lyE5G",
                dateTime = "2024-11-01T17:33:31.000+09:00",
                mediaUrl = "http://www.youtube.com/watch?v=GBwOIGnNjfg",
                sources = "경제적자유민족",
                title = "노머스, 닷밀, 쓰리빌리언 청약 전 마지막 체크",
                playTime = 287
            ),
            MediaVideo(
                thumbnail = "https://search2.kakaocdn.net/argon/138x78_80_pr/L7X2nBffJdj",
                dateTime = "2024-11-07T23:19:49.000+09:00",
                mediaUrl = "http://www.youtube.com/watch?v=pTQSomwn5Rs",
                sources = "빨간조끼아저씨",
                title = "노머스..균등폭탄....헉....  균등만 95주 배정,,",
                playTime = 200
            ),
            MediaVideo(
                thumbnail = "https://search1.kakaocdn.net/argon/138x78_80_pr/KSyI0oWKnmy",
                dateTime = "2024-11-12T15:54:17.000+09:00",
                mediaUrl = "http://www.youtube.com/watch?v=3dylOtTEBuQ",
                sources = "공모주린이의 투자백서",
                title = "[공모주 상장 LIVE] 노머스 공모주 11/12일 상장일 주가흐름 LIVE",
                playTime = 27609
            ),
            MediaVideo(
                thumbnail = "https://search2.kakaocdn.net/argon/138x78_80_pr/pW9vsNa5wv",
                dateTime = "2024-11-05T16:25:30.000+09:00",
                mediaUrl = "http://www.youtube.com/watch?v=J0meS4_zzMg",
                sources = "공모주린이의 투자백서",
                title = "[공모주 경쟁률 LIVE] 노머스 & 닷밀 공모주 11/5일 2일차 실시간 경쟁률 LIVE",
                playTime = 28638
            )
        )

        // Then : 비디오 데이터가 저장된다.
        val actual = searchViewModel.videoList.value
        for (i in 0 until 5) {
            assertThat(actual[i]).isEqualTo(expect[i])
        }
    }
}