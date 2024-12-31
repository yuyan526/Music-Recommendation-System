# 爬取音乐详细信息
# 动态爬虫
from selenium import webdriver

# webdriver实例化，因为需要进入到iframe中获取数据，所以需要使用selenium
option = webdriver.ChromeOptions()
# 设置option，不弹出显示框
option.add_argument('headless')
# 调用带参数的谷歌浏览器
driver = webdriver.Chrome(options=option)


def get_songs_playcnt():
    """
    得到歌曲播放次数
    :return:
    """
    songs_playcnt = {}
    with open('./dataset/min_user_record.txt', 'r', encoding='utf-8') as f:
        for line in f:
            line = line.strip()
            song_id = line.split('\t')[1]
            weight = int(line.split('\t')[2].strip())
            songs_playcnt.setdefault(song_id, int(songs_playcnt.get(song_id, 0)) + weight)
    f.close()
    return songs_playcnt


# print(get_song_playcnt().get('1354477202'))

def get_songs_album():
    """
    获得歌曲所在专辑，歌曲id到专辑名称映射
    :return:
    """
    songs_album = {}
    with open('./dataset/song_album.txt', 'r', encoding='utf-8') as f:
        for line in f:
            song_id = line.split('\t')[0]
            song_album = line.split('\t')[1].strip()
            songs_album[song_id] = song_album
    f.close()
    return songs_album


def get_album(song_url):
    print('发送请求')
    driver.get(song_url)
    # 找到指定iframe标签（这里是g_iframe）然后跳入
    driver.switch_to.frame('g_iframe')
    driver.implicitly_wait(10)  # 隐式等待
    try:
        album = driver.find_element_by_class_name('m-lycifo').find_element_by_class_name(
            'cnt').find_elements_by_tag_name('p')
    except:
        album = '歌曲无法找到'
        return album
    # album = driver.find_element_by_class_name('m-lycifo').find_element_by_class_name('cnt').find_elements_by_tag_name(
    #     'p')
    print(album)
    if len(album) > 1:
        album = album[1].text.split('：')
        if len(album) > 1:
            album = album[1]
        else:
            album = '暂无专辑'
    else:
        album = '暂无专辑'
    print(album)
    return album


# get_album('https://music.163.com/#/song?id=1805305069')

def get_song_info():
    """
    补充歌曲信息
    :return:
    """
    with open('./dataset/min_song_info.txt', 'r', encoding='utf-8') as f, open('./dataset/song_album.txt', 'a',
                                                                               encoding='utf-8') as out_f:
        for line in f:
            song_id = line.split('\t')[0]
            song_album = get_album(line.split('\t')[2].strip())
            print(song_id + '\t' + song_album)
            out_f.write(song_id + '\t' + song_album + '\n')
            out_f.flush()
        f.close()
        out_f.close()


# get_song_info()

def get_new_min_song_info():
    """
    得到新的音乐信息，添加了播放次数和专辑信息
    :return:
    """
    songs_playcnt = get_songs_playcnt()
    print(songs_playcnt)
    songs_album = get_songs_album()
    with open('./dataset/min_song_info.txt', 'r', encoding='utf-8') as f, open('./dataset/new_min_song_info.txt', 'a',
                                                                               encoding='utf-8') as out_f:
        for line in f:
            line = line.strip()
            song_id = line.split('\t')[0]
            song_name = line.split('\t')[1]
            song_url = line.split('\t')[2]
            song_album = songs_album[song_id]
            song_playcnt = songs_playcnt[song_id]
            singer_id = line.split('\t')[3]
            singer_name = line.split('\t')[4]
            singer_url = line.split('\t')[5].strip()
            line = song_id + '\t' + song_name + '\t' + song_url + '\t' + song_album + '\t' + str(song_playcnt) + '\t' + singer_id + '\t' + singer_name + '\t' + singer_url
            print(line)
            out_f.write(line + '\n')
            out_f.flush()
    f.close()
    out_f.close()


# get_new_min_song_info()
