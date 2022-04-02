package com.example.SocialApi;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SocialApiApplication {

	public static void main(String[] args) throws UnirestException {
		SpringApplication.run(SocialApiApplication.class, args);
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = Unirest.get("https://www.tiktok.com/auth/authorize/?client_key=awzppo5n91dlfny1&response_type=code&scope=user.info.basic,video.list,share.sound.create,video.upload&redirect_uri=https://example.com/&state=")
				.header("Cookie", "_abck=E6F66484A1C925432D33501C05661D80~-1~YAAQXm8/F3m7uZ1/AQAASbQH5AfOQunhWfhwrEJ3Tlpqnl4mkSNR5JdyBP+jKut5+QnNuopop/N8xEP7kQluSxNcrgZPCdb6qUI0O44zn088g8SpbwctrSIDLoBV0lD32ZvK4lnXXwkX2shucmK+Y1I8G1Vb6wZBjkn5TgtLAYUbbaw/x2o07zAUuTbYSBl0H7+kL0NofxLOS6HcnwwIn7eabF1hOky0tlhzjvxie//5UNNKNNHA9mpFwuVGwiVEM4br1mAux3NK51bK3lR7lLpmSgHsOtRH3hWvrIRck8ZPD728D5H/RGfzpO+EMJq4I0UIcY1VRppzF2pnOJWUmvETbBmDkzyfIPka2tqBxO6hgohmcU14sLqbSNflBpUY3AE1W0D23g==~-1~-1~-1; ak_bmsc=558DD9D4019ABC61A950B467B02A98D8~000000000000000000000000000000~YAAQXm8/F3u7uZ1/AQAAsrUH5A+JChJ+KWAOk9P7dXfF5xsGxozo/QvEcCf5DlGQdUC5N/jIuQMAlWZOgvWUs9ihJ5laYFx1cPNQ1Z+rhsQYpICq6Q7QVMtBW1M0fqRl5mUyKgztdPa0H/BsPgfR/04TOmv/Gy0EM1QBiXdRjNrZmwSOXMI13gSdny1g+3pD7kbOR0orG6xLulC8ju4T+qk8gwVowAtYof4/T3T+NnfydBAO2pB9dE8HLcjBGSEGJ5sfYGfinc953hk9iGKxgDWxtOJ8Kh2KyerW6Sk5OOY1PLmKz6fkKKHFtoaFV5HiWo01mMU7GwOBlgYe443jQoJSgWRdFca+OqYeCVferytyJT/jbPLRqcYCd2U=; bm_mi=1B673F25290FD87B6DD1A2FE4BD232E9~AekkJotJlI2+CQ1b4S0Lrcr				wQJNbhiZq9Xa+igco/iOj51s5RGBFSwRPnr5O+OQD2j82zOi2/wUKITSAnLcBrtsDIk+NmgZzFNifvmWOvR6N0QLnnYA0sa+VOthfRRwDACbLhKfQzk7WssIsKtiUnM+RUBKDwpc76JG8l8QpNGTj7eKbdq3k4vff8bIy4G2zIYbefMXQWarD7HMHhFeyg3m2fT66026nrzNVgLvy4hc=; bm_sv=E1D580DCCFEFA7B50DCCEE5774634DB0~UVaNTvegKaR9yW764DBS568ngaViznpnlW+wzPgL2SYX0w2dBOS/AOYGX7YMfH1+U3banGGC3jRI2c4mAdX1GRyHOHpCPzbQHliKQl++55jfCV6Oj5U0Yj/ci7qTaycTeVZQYco7RhFYprHOslS3z7qOQwU664A8XfN3xSeDHyU=; bm_sz=CCF77DFD1281CAEAC8F4D8E98C0D7A39~YAAQXm8/F3q7uZ1/AQAASbQH5A/uxeZlaNOD9OMKwKPTpsXkNNFp4zNZ3sHVRZl8nfBwW8zcwdIsxt6EGL3ZXI2DfT6vf1MrapSuDnsISMcu81K48SUEnLt1NT4i36qTzdE3r5X4k1l40RWzTG3aHOTradm9ek+v8Pbk/hL4e22fMcMtdlWAaWxaAOXxQ4PPWbPPi69shgDK1i9ciDls8Xd1TWa7xOEy7P2i53kIlsPECqn/SX3axMDS/yXSQUbyCawLm258x8NYNzVAlqveN/0DuGDxpUIvVcoyeZUb07oF20k=~3556664~3420726; msToken=MkM6wpRNLxjPQ7G6j0B56LgUINwQYdV3evq1xNDJ0IOAiAjXYJa_o25z202VsQ3Afu9TBI0zEomGRFFE8FqZDuxC; passport_csrf_token=b1139c8070caabff1ee774a008bf9ffa; passport_csrf_token_default=b1139c8070caabff1ee774a008b				f9ffa; tt_csrf_token=sli5N7yZ-D17FXDv1S8wzDz1; ttwid=1%7CCGNHJAGWYB84Tx3aKit487RLPyqF_o-cdl0ccys1QO4%7C1648798184%7C344479aba157dc8f80b5e80823f3b4ad2801a95e708731b57c2ea0117fa21f2d")
				.asString();

		System.out.println(response.getBody() + response.getStatus());
	}

}
