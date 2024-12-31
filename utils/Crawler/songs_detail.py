# 爬取网易云音乐的详细信息下载地址，使用接口：https://api.imjad.cn/cloudmusic/?type=detail&id=xxxxx  获得歌曲的播放连接
import requests
import json


def get_song_time_picurl(song_id):
    """
    通过接口：https://api.imjad.cn/cloudmusic/?type=detail&id=xxxxx  获得歌曲播放连接
    :param song_id: 歌曲id
    :return: 返回data的内容
    """
    response = requests.get(
        "https://api.imjad.cn/cloudmusic/?type=detail&id=" + song_id)
    if response.status_code == 200:
        if len(response.json()['songs']) < 1:
            return 'null', 'nlll'
        song_millseconds = response.json()['songs'][0].get('dt')
        song_picurl = response.json()['songs'][0].get('al').get('picUrl')
    else:
        song_millseconds = 'null'
        song_picurl = 'null'
    print("时长：" + str(song_millseconds) + "，图片链接：" + song_picurl)
    return song_millseconds, song_picurl


# 获取时长和图片信息
# get_song_time_picurl("448317748")

def get_song_publish_time(song_id):
    """
    通过接口：https://api.imjad.cn/cloudmusic/?type=detail&id=xxxxx  获得歌曲播放连接
    :param song_id: 歌曲id
    :return: 返回data的内容
    """
    response = requests.get(
        "https://api.imjad.cn/cloudmusic/?type=detail&id=" + song_id)
    if response.status_code == 200:
        if len(response.json()['songs']) < 1:
            return 'null', 'null'
        song_detail = response.json()['songs'][0]
        song_publish_time = song_detail.get('publishTime')
        print("发行时间：" + str(song_publish_time))
        return song_publish_time, json.dumps(song_detail)
    else:
        song_publish_time = 'null'
        song_detail = 'null'
        print("发行时间：" + str(song_publish_time))
        return song_publish_time, song_detail


# song_publish = get_song_publish_time("1331513736")
# print(song_publish)


def get_song_detail(song_id):
    """
    通过接口：https://api.imjad.cn/cloudmusic/?type=detail&id=xxxxx  获得歌曲播放连接
    :param song_id: 歌曲id
    :return: 返回data的内容
    """
    response = requests.get(
        "https://api.imjad.cn/cloudmusic/?type=detail&id=" + song_id)
    if response.status_code == 200:
        if len(response.json()['songs']) < 1:
            return 'null'
        song_detail = response.json()['songs'][0]
        print("歌曲细节：" + str(song_detail))
        return json.dumps(song_detail)
    else:
        song_detail = 'null'
        print("歌曲细节：" + str(song_detail))
        return song_detail


def get_songs_id():
    """
    获得歌曲id
    :return: 歌曲id数组
    """
    songs_id = []
    with open('./dataset/min_song_info_1.txt', 'r', encoding='utf-8') as f:
        for line in f:
            song_id = line.split('\t')[0]
            songs_id.append(song_id)
    f.close()
    return songs_id


songs_id = get_songs_id()
print("已获得所有歌曲id")


def get_all_songs_time_picurl():
    """
    获得所有歌曲的时长和图片链接
    :return:
    """
    with open("./dataset/songs_time_picurl.txt", 'a', encoding='utf-8') as f:
        for id in songs_id:
            song_time, song_picurl = get_song_time_picurl(id)
            f.write(id + '\t' + str(song_time) + '\t' + song_picurl + '\n')
            f.flush()
    f.close()


# get_all_songs_time_picurl()

def get_all_songs_publish_time():
    """
    获得歌曲的发行时间
    :return:
    """
    # 这里从13980行开始收集歌曲详情信息，13980以前的后续在进行收集
    with open("./dataset/songs_publish_time.txt", 'a', encoding='utf-8') as f, open("./dataset/songs_detail_13980.txt",
                                                                                    "a", encoding="utf-8") as out_f:
        for id in songs_id:
            song_publish_time, song_detail = get_song_publish_time(id)
            f.write(id + '\t' + str(song_publish_time) + '\n')
            f.flush()
            out_f.write(id + '\t' + song_detail + '\n')
            out_f.flush()
    f.close()
    out_f.close()


# get_all_songs_publish_time()


def get_all_songs_detail():
    """
    获得歌曲的发行时间
    :return:
    """
    # 这里从13980行开始收集歌曲详情信息，13980以前的后续在进行收集
    with open("./dataset/songs_detail_13980.txt","a", encoding="utf-8") as out_f:
        for id in songs_id:
            song_detail = get_song_detail(id)
            out_f.write(id + '\t' + song_detail + '\n')
            out_f.flush()
    out_f.close()

# get_all_songs_detail()