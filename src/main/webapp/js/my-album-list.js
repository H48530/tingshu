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
            var html = `<l1>
               <a href="/album-editor.html?aid=${album.aid}">
               <img src="${album.cover}">
               <h2>${album.name}</h2>
               <p>共播放 ${album.count} 次 </p>
               </a>
               </l1>`
            olElement.innerHTML += html
        }
    }

    var url = "/api/my-album-list.json"
    ajax("get", url, onResult)
}

window.addEventListener("load", main)