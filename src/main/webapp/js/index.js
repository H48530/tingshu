function main() {
    var olElement = document.querySelector("ol.album-list")
    header()

    function onResult(result) {
        if (!result.success) {
            alert(result.reason)
            return
        }
        var albumList = result.data
        for (var album of albumList) {
            var html = `<li>
        <a href="/album-detail.html?aid=${album.aid}">
          <img src="${album.cover}">
          <h2>${album.name}</h2>
          <p>共播放 ${album.count} 次</p>
        </a>
      </li>`
            olElement.innerHTML += html
        }
    }

    var keyword = getParameter("keyword")

    if (keyword) {
        var url = "/api/album-list.json?keyword=" + encodeURIComponent(keyword)
    } else {
        var url = "/api/album-list.json"
    }
    ajax("get", url, onResult)
}

window.addEventListener("load", main)