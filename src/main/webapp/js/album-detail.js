function main() {
    header()

    var albumMeta = document.querySelector(".meta")
    var storyList = document.querySelector(".story-list")
    var aid = getParameter("aid")
    if (!aid) {
        alert("必须带有aid")
        return
    }
    var url = "/api/album-detail.json?aid=" + aid
    ajax("get", url, function (result) {
        if (!result.success) {
            alert(result.reason)
            return
        }
        var album = result.data
        var html = `<h1>${album.name}</h1><img src="${album.cover}">`
        albumMeta.innerHTML = html

        for (var story of album.storyList) {
            var liHtml = `<li><a href="/story-detail.html?sid=${story.sid}">
        <h3>${story.name}</h3>
        <span>共播放：${story.count}</span>
      </a></li>`
            storyList.innerHTML += liHtml
        }
    })
}

window.addEventListener("load", main)