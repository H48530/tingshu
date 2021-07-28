function main() {
    header()

    var albumMeta = document.querySelector(".meta")
    var storyList = document.querySelector(".story-list")
    var newStoryBtn = document.querySelector("#new-story")
    var aid = getParameter("aid")
    if (!aid) {
        alert("必须带有aid")
        return
    }
    newStoryBtn.href += ("?aid=" + aid)
    var url = "/api/album-editor.json?aid=" + aid;
    ajax("get", url, function (result) {
        if (!result.success) {
            alert(result.reason)
            return
        }
        var album = result.data
        var html = `<h1>${album.name}</h1><img src="${album.cover}">`
        albumMeta.innerHTML = html;
        for (var story of album.storyList) {
            var liHTML = `<li><h3>${story.name}</h3></li>`
            storyList.innerHTML += liHTML
        }
    })
}

window.addEventListener("load", main)