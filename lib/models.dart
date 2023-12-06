class Permission {
  const Permission({
    required this.type,
    this.kind,
    this.checked = true,
  });

  final String type;
  final int? kind;
  final bool checked;

  Map<String, dynamic> toJson() {
    return {
      "type": type,
      "int": kind,
      "checked": checked,
    };
  }
}
