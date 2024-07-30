# BunnyBooru

This app converts the InkBunny API to the Danbooru 2 API, which will allow you to use Booru clients to view InkBunny
posts. Personally, I use [AnimeBoxes](https://www.animebox.es/) on android

## Setup

You need installed java 17. After that, just launch jar file. You can found it
in [releases](https://github.com/Split50/BunnyBooru/releases)

```bash
java -jar BunnyBooru-1.0.0.jar
```

After that, 8080 port should be available.
You need use standard Danbooru 2 auth method, and write password instead of api_key field

## Compatibility

I was not trying to migrate all booru features such as synchronization of favorites.

I needed to have a read-only feature. However, I made some handy tags to improve the interaction with inkbunny.

* a:[author_name] - Limit search by author
* pool:[pool_id]:description - Limit search by pool
  These tags are automatically set in all submissions if they are present

## Selfhost

You need to download and host it on your computer. If you use a phone like I do, you can bind a static ip on your local
network and then configure the phone's booru client to its ip. For example, bind the PC to `192.168.0.20`, and then
configure the booru client on the phone to `http://192.168.0.20:8080`.

Even though the application supports multiple users, I won't host a public instance because: **Security**. 

I don't think anyone would want to trust me with their username and password