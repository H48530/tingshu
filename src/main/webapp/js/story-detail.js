function main() {
    header()
    // 1. 从 url 上获取 sid
    var sid = getParameter("sid")
    if (!sid) {
        alert("必须带有 sid")
        return
    }

    var url = "/api/story-detail.json?sid=" + sid
    var h1 = document.querySelector("h1")
    var audio = document.querySelector("audio")

    ajax("get", url, function (result) {
        if (!result.success) {
            alert(result.reason)
            return
        }

        var story = result.data
        h1.innerText = story.name
        audio.src = story.audio
    })
}

window.addEventListener("load", main)
