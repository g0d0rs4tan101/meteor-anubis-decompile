# Anubis 0.9.9.1 — Decompiled-source analysis

This directory contains a best-effort Java decompilation of the Anubis Minecraft
client mod from `Anubis_Beta_0_9_9_1.jar`. This README records what can be
established from the JAR metadata and recovered code. The JAR was not run.

## Summary

The JAR presents itself as a client-side Minecraft utility mod, with overlays,
quality-of-life features, and visual debugging tools. Its Fabric metadata
declares the mod ID `anubisplus`, version `0.9.9.1+mc1.21.11`, and GPL-3.0-only
license. The manifest separately records Minecraft `1.21`; that version
metadata is inconsistent.

More importantly, the recovered code contains a credential-exfiltration path:
the mod obtains the Minecraft session access token and includes it in an
outbound request to a remote service. Its custom TLS client also accepts any
server certificate. These are concrete, high-confidence indicators of
malicious behavior.

The code additionally contains a remote-loader path that downloads and extracts
an archive, then starts a process from it. Static inspection does not establish
what the remote service returns, whether that path succeeds, or what a returned
payload would do. Treat that behavior as highly suspicious, but do not mistake
it for analysis of the unknown payload itself.

## Observed behavior

- **Minecraft session token collection and transmission:** the Fabric
  initializer calls `vgxu_lwymi_capgk.kzZpom()` to obtain the session token and
  passes it into the request-building flow. The resulting body is sent by the
  custom HTTP client. See
  [`mNIrulVZAELh.java`](net/fabric/mNIrulVZAELh.java),
  [`vgxu_lwymi_capgk.java`](net/fabric/vgxu_lwymi_capgk.java), and
  [`bHZHd.java`](net/fabric/bHZHd.java).
- **TLS certificate validation is disabled:** the custom trust manager's
  `checkServerTrusted` method is empty, so it does not verify the server
  certificate chain. See [`PAYym8FkkE.java`](net/fabric/PAYym8FkkE.java).
- **Remote archive and process-launch behavior:** the code includes a
  background subprocess path, archive extraction, and process creation. The
  contents and behavior of any remotely supplied payload are unknown from this
  static review. See [`mNIrulVZAELh.java`](net/fabric/mNIrulVZAELh.java) and
  [`bHZHd.java`](net/fabric/bHZHd.java).

## Source recovery and limitations

The Java files were generated from the supplied JAR with CFR 0.152. They are
decompiled output, not the original source: names and control flow may be
obfuscated or imperfectly reconstructed. The decompiler summary records
11 methods with unresolved type clashes, structuring failures, or other
decompilation errors; see [`summary.txt`](summary.txt).

The decompilation produced 668 Java files. Fourteen nested dependency JARs
inside the original JAR were not expanded or decompiled, and bundled native
libraries were not analyzed. This directory is therefore not a complete
reconstruction of every bytecode component.

This was a static review only. The original JAR and any downloaded payload were
not executed. The remote service's identity and responses, whether the loader
successfully runs, and the behavior of unreviewed dependencies remain unknown.

## Handling advice

Do not install or run the original JAR to investigate it. If it has already
been run, revoke the affected Minecraft session/token and review the machine
for unexpected files or processes. Preserve the original JAR and its hash when
sharing this analysis so others can verify they are examining the same sample.
