package com.baidu.wdyy.bean;

import java.util.List;

public class MoiveBean {
        /**
         * followMovie : 2
         * id : 17
         * imageUrl : http://172.17.8.100/images/movie/stills/ftfb3/ftfb(3)1.jpg
         * name : 反贪风暴3
         * rank : 0
         * releaseTime : 1537545600000
         * releaseTimeShow : 2018-09-22
         * summary : ICAC (廉政公署) 陆志廉（古天乐 饰），JFIU (联合财富情报组) 刘保强（张智霖 饰）分别侦查贪污及洗黑钱案，但苦无线索，这时廉政公署L组 (内部纪律调查组) 程德明（郑嘉颖 饰）收到举报，指陆志廉收贿1200万，陆无法辩解实时停职。刘发现陆被诬陷，并跟一直调查的洗黑钱案有着千丝万缕关系，同时怀疑银行主任游子新（栢天男 饰）协助罪恶集团洗黑钱；陆冒险搜集罪证却遭禁锢，命悬一线.......
         */

        private int followMovie;
        private int id;
        private String imageUrl;
        private String name;
        private int rank;
        private long releaseTime;
        private String releaseTimeShow;
        private String summary;

        public int getFollowMovie() {
            return followMovie;
        }

        public void setFollowMovie(int followMovie) {
            this.followMovie = followMovie;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public long getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(long releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getReleaseTimeShow() {
            return releaseTimeShow;
        }

        public void setReleaseTimeShow(String releaseTimeShow) {
            this.releaseTimeShow = releaseTimeShow;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
}
