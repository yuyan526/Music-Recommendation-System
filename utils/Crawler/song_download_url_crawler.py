# 爬取网易云音乐的歌曲下载地址，使用接口：https://api.imjad.cn/cloudmusic/?type=song&id=xxxxx  获得歌曲的播放连接
import requests
import json


def get_songs_id():
    """
    获得歌曲id
    :return: 歌曲id数组
    """
    songs_id = []
    with open('./dataset/new_min_song_info_1.txt', 'r', encoding='utf-8') as f:
        for line in f:
            song_id = line.split('\t')[0]
            songs_id.append(song_id)
    f.close()
    return songs_id


# songs_id = get_songs_id()
# print(songs_id)

def get_song_download_url(song_id):
    """
    通过接口：https://api.imjad.cn/cloudmusic/?type=song&id=xxxxx  获得歌曲播放连接
    接口：http://music.163.com/api/song/enhance/player/url?id=454828887&ids=%5B454828887%5D&br=3200000
    :param song_id: 歌曲id
    :return: 返回data的内容
    """
    response = requests.get(
        "http://music.163.com/api/song/enhance/player/url?id=" + song_id + "&ids=%5B" + song_id + "%5D&br=3200000")
    if response.status_code == 200:
        song_url = response.json()['data'][0].get('url')
    else:
        song_url = 'null'
    print(song_url)
    return song_url


# response = get_song_download_url("1356499052")
# print(response)

def get_songs_download_url():
    """
    获得所有歌曲的播放链接
    :return:
    """
    songs_id = get_songs_id()
    # songs_download_url = {}
    # 获得歌曲的播放连接
    # for song_id in songs_id:
    #     songs_download_url[song_id] = get_song_download_url(song_id)
    #     print(songs_download_url[song_id])
    with open("./dataset/songs_download_url.txt", 'a', encoding='utf-8') as f:
        for song_id in songs_id:
            line = song_id + '\t' + str(get_song_download_url(song_id))
            print(line)
            f.write(line + '\n')
            f.flush()
    f.close()


get_songs_download_url()
