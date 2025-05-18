package org.javaclimb.springbootmusic.constants;

public class FilePaths {
    public static final String UPLOAD_PATH = "upload/";  // 基础上传路径
    public static final String PORT_PATH = "http://localhost:8080/";//端口号
    
    public static final String SONGS_PATH = UPLOAD_PATH + "songs/";  // 歌曲上传路径
    public static final String ALBUMS_PATH = UPLOAD_PATH + "albums/";  // 专辑上传路径
    public static final String ARTISTS_PATH = UPLOAD_PATH + "artists/";  // 歌手上传路径
    public static final String USERS_PATH = UPLOAD_PATH + "users/";  // 用户上传路径
    public static final String PLAYLIST_PATH = UPLOAD_PATH + "playlists/";  // 歌单上传路径
    
    public static final String SONG_AUDIO_PATH = SONGS_PATH + "audio/";  // 歌曲音频路径
    public static final String SONG_LYRIC_PATH = SONGS_PATH + "lyric/";  // 歌曲歌词路径
    public static final String ALBUM_COVER_PATH = ALBUMS_PATH + "cover/";  // 专辑封面路径
    public static final String ARTIST_AVATAR_PATH = ARTISTS_PATH + "avatar/";  // 歌手头像路径
    public static final String USER_AVATAR_PATH = USERS_PATH + "avatar/";  // 用户头像路径
    public static final String PLAYLIST_COVER_PATH = PLAYLIST_PATH + "cover/";  // 歌单封面路径
}

