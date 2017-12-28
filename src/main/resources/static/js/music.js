window.onload = function () {
    cplayer.prototype.add163 = function add163(id) {
        if (!id) throw new Error("Unable Property.");
        return fetch("https://music.huaji8.top/?id=" + id).then(function (res) {
            return res.json()
        }).then(function (data) {
            var obj = {
                name: data.info.songs[0].name,
                artist: data.info.songs[0].ar.map(function (ar) {
                    return ar.name
                }).join(','),
                poster: data.pic.url.replace('http://', 'https://'),
                lyric: data.lyric.lyric,
                sublyric: data.lyric.tlyric,
                src: data.url.url.replace('http://', 'https://'),
                album: data.info.songs[0].al.name
            };
            this.add(obj);
            return obj;
        }.bind(this))
    };

    var player = new cplayer({
        element: document.getElementById('cplayer'),
        zoomOutKana: true,
        autoplay: true,
        volume: 0.75,
        dropDownMenuMode: 'bottom'
    });
    player.add163(167766);
    player.add163(168089);
    player.add163(167880);
    player.add163(27180681);
    player.add163(167975);
    player.add163(25906124);
    player.add163(4164317);
    player.add163(18374823);
    player.add163(25731674);
    player.add163(476987525);
};