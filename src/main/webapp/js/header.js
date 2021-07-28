function header() {
    var functionDiv = document.querySelector(".function")

    function onResult(result) {
        if (result.logged) {
            functionDiv.innerHTML = '<a href="/new-album.html">新建专辑</a><a href="/my-album-list.html">我的专辑</a>'
        }
    }

    ajax("get", "api/current-user.json", onResult)
}