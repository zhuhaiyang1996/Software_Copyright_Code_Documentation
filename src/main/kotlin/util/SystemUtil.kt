package util

object SystemUtil {

    /**
     * @return null
     * @author
     * @description 获取操作系统名称
     * @date 7/1/22 3:25 PM
     */
    @JvmStatic
    fun getOS(full: Boolean): String {
        /**
         * System properties. The following properties are guaranteed to be defined:
         * <dl>
         * <dt>java.version         <dd>Java version number
         * <dt>java.version.date    <dd>Java version date
         * <dt>java.vendor          <dd>Java vendor specific string
         * <dt>java.vendor.url      <dd>Java vendor URL
         * <dt>java.vendor.version  <dd>Java vendor version
         * <dt>java.home            <dd>Java installation directory
         * <dt>java.class.version   <dd>Java class version number
         * <dt>java.class.path      <dd>Java classpath
         * <dt>os.name              <dd>Operating System Name
         * <dt>os.arch              <dd>Operating System Architecture
         * <dt>os.version           <dd>Operating System Version
         * <dt>file.separator       <dd>File separator ("/" on Unix)
         * <dt>path.separator       <dd>Path separator (":" on Unix)
         * <dt>line.separator       <dd>Line separator ("\n" on Unix)
         * <dt>user.name            <dd>User account name
         * <dt>user.home            <dd>User home directory
         * <dt>user.dir             <dd>User's current working directory
         * </dl>
         */
        val os = System.getProperty("os.name").lowercase()
        val arch = System.getProperty("os.arch")
        val version = System.getProperty("os.version")
        if (full) {
            return when {
                os.contains("win") -> "Windows $version $arch"
                os.contains("nix") || os.contains("nux") || os.contains("aix") -> "Linux $version $arch"
                os.contains("mac") -> "Mac OS $version $arch"
                os.contains("sunos") -> "Sun OS $version $arch"
                else -> "未知操作系统"
            }
        } else {
            return when {
                os.contains("win") -> "Windows"
                os.contains("nix") || os.contains("nux") || os.contains("aix") -> "Linux"
                os.contains("mac") -> "Mac OS"
                os.contains("sunos") -> "Sun OS"
                else -> "未知操作系统"
            }
        }
    }
}